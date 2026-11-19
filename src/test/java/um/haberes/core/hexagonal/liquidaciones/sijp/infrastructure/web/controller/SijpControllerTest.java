package um.haberes.core.hexagonal.liquidaciones.sijp.infrastructure.web.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import um.haberes.core.hexagonal.liquidaciones.sijp.application.exception.SijpException;
import um.haberes.core.hexagonal.liquidaciones.sijp.application.exception.SijpSinControlException;
import um.haberes.core.hexagonal.liquidaciones.sijp.application.service.SijpService;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.ArchivoSijp;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.VarianteSijp;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.layout.SijpLayout;
import um.haberes.core.hexagonal.liquidaciones.sijp.infrastructure.web.mapper.SijpDtoMapper;

@ExtendWith(MockitoExtension.class)
class SijpControllerTest {

    private static final Integer ANHO = 2026;
    private static final Integer MES = 11;
    private static final byte[] CONTENIDO = "linea de prueba".getBytes(SijpLayout.CHARSET_ANSI);

    @Mock
    private SijpService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        SijpController controller = new SijpController(service, new SijpDtoMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void generarDevuelve200ConElArchivoEnBase64() throws Exception {
        when(service.generar(ANHO, MES, VarianteSijp.NORMAL)).thenReturn(archivo());

        mockMvc.perform(get("/api/haberes/core/sijp/{anho}/{mes}/{variante}", ANHO, MES, "normal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreArchivo").value("sijp-2026-11.txt"))
                .andExpect(jsonPath("$.variante").value("NORMAL"))
                .andExpect(jsonPath("$.contenido").value(Base64.getEncoder().encodeToString(CONTENIDO)))
                .andExpect(jsonPath("$.cantidadRegistros").value(3))
                .andExpect(jsonPath("$.omitidos").isEmpty())
                .andExpect(jsonPath("$.advertencias").isEmpty());
    }

    @Test
    void generarAceptaLaVarianteEtec() throws Exception {
        when(service.generar(ANHO, MES, VarianteSijp.ETEC))
                .thenReturn(archivo(ANHO, MES, VarianteSijp.ETEC));

        mockMvc.perform(get("/api/haberes/core/sijp/{anho}/{mes}/{variante}", ANHO, MES, "etec"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreArchivo").value("sijp-2026-11-etec.txt"))
                .andExpect(jsonPath("$.variante").value("ETEC"));
    }

    @Test
    void unaVarianteInvalidaDevuelve400() throws Exception {
        mockMvc.perform(get("/api/haberes/core/sijp/{anho}/{mes}/{variante}", ANHO, MES, "otra"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void unPeriodoSinControlDevuelve404() throws Exception {
        when(service.generar(ANHO, MES, VarianteSijp.NORMAL))
                .thenThrow(new SijpSinControlException(ANHO, MES));

        mockMvc.perform(get("/api/haberes/core/sijp/{anho}/{mes}/{variante}", ANHO, MES, "normal"))
                .andExpect(status().isNotFound());
    }

    @Test
    void unPeriodoSinLiquidadosDevuelve422() throws Exception {
        when(service.generar(ANHO, MES, VarianteSijp.NORMAL))
                .thenThrow(new SijpException("No hay legajos liquidados en el período 2026/11"));

        mockMvc.perform(get("/api/haberes/core/sijp/{anho}/{mes}/{variante}", ANHO, MES, "normal"))
                .andExpect(status().isUnprocessableEntity());
    }

    private ArchivoSijp archivo() {
        return archivo(ANHO, MES, VarianteSijp.NORMAL);
    }

    private ArchivoSijp archivo(Integer anho, Integer mes, VarianteSijp variante) {
        String sufijo = variante == VarianteSijp.ETEC ? "-etec" : "";
        return ArchivoSijp.builder()
                .variante(variante)
                .nombreArchivo("sijp-" + anho + "-" + mes + sufijo + ".txt")
                .contenido(CONTENIDO)
                .cantidad(3)
                .build();
    }

}
