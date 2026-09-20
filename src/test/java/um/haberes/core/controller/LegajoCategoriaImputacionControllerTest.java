package um.haberes.core.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.kotlin.model.LegajoCategoriaImputacion;
import um.haberes.core.service.LegajoCategoriaImputacionService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LegajoCategoriaImputacionControllerTest {

    @Mock
    private LegajoCategoriaImputacionService service;

    @InjectMocks
    private LegajoCategoriaImputacionController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private LegajoCategoriaImputacion sampleLegajoCategoriaImputacion() {
        LegajoCategoriaImputacion legajoCategoriaImputacion = new LegajoCategoriaImputacion();
        legajoCategoriaImputacion.setLegajoCategoriaImputacionId(21L);
        legajoCategoriaImputacion.setLegajoId(123L);
        legajoCategoriaImputacion.setAnho(2024);
        legajoCategoriaImputacion.setMes(6);
        legajoCategoriaImputacion.setDependenciaId(2);
        legajoCategoriaImputacion.setFacultadId(3);
        legajoCategoriaImputacion.setGeograficaId(4);
        legajoCategoriaImputacion.setCategoriaId(9);
        legajoCategoriaImputacion.setCuentaSueldos(new BigDecimal("30000"));
        legajoCategoriaImputacion.setBasico(new BigDecimal("20000.00"));
        legajoCategoriaImputacion.setAntiguedad(new BigDecimal("1500.50"));
        legajoCategoriaImputacion.setCuentaAportes(new BigDecimal("5000"));
        return legajoCategoriaImputacion;
    }

    private String legajoCategoriaImputacionJson() {
        return """
                {
                  "legajoCategoriaImputacionId": 21,
                  "legajoId": 123,
                  "anho": 2024,
                  "mes": 6,
                  "dependenciaId": 2,
                  "facultadId": 3,
                  "geograficaId": 4,
                  "categoriaId": 9,
                  "cuentaSueldos": 30000,
                  "basico": 20000.00,
                  "antiguedad": 1500.50,
                  "cuentaAportes": 5000,
                  "persona": null,
                  "dependencia": null,
                  "facultad": null,
                  "geografica": null,
                  "categoria": null,
                  "created": null,
                  "updated": null
                }
                """;
    }

    @Test
    void findAllByLegajo_returnsOkWithListOfLegajoCategoriaImputacions() throws Exception {
        when(service.findAllByLegajo(123L, 2024, 6)).thenReturn(List.of(sampleLegajoCategoriaImputacion()));

        mockMvc.perform(get("/api/haberes/core/legajocategoriaimputacion/legajo/{legajoId}/{anho}/{mes}", 123, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + legajoCategoriaImputacionJson() + "]", JsonCompareMode.STRICT));
    }
}
