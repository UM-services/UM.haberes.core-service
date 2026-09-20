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
import um.haberes.core.kotlin.model.LegajoCodigoImputacion;
import um.haberes.core.service.LegajoCodigoImputacionService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LegajoCodigoImputacionControllerTest {

    @Mock
    private LegajoCodigoImputacionService service;

    @InjectMocks
    private LegajoCodigoImputacionController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private LegajoCodigoImputacion sampleLegajoCodigoImputacion() {
        LegajoCodigoImputacion legajoCodigoImputacion = new LegajoCodigoImputacion();
        legajoCodigoImputacion.setLegajoCodigoImputacionId(1L);
        legajoCodigoImputacion.setLegajoId(100L);
        legajoCodigoImputacion.setAnho(2024);
        legajoCodigoImputacion.setMes(6);
        legajoCodigoImputacion.setDependenciaId(7);
        legajoCodigoImputacion.setFacultadId(3);
        legajoCodigoImputacion.setGeograficaId(4);
        legajoCodigoImputacion.setCodigoId(55);
        legajoCodigoImputacion.setCuentaSueldos(new BigDecimal("10.10"));
        legajoCodigoImputacion.setImporte(new BigDecimal("20.20"));
        legajoCodigoImputacion.setCuentaAportes(new BigDecimal("30.30"));
        return legajoCodigoImputacion;
    }

    @Test
    void findAllByLegajo_returnsOkWithListOfLegajoCodigoImputacion() throws Exception {
        when(service.findAllByLegajo(100L, 2024, 6)).thenReturn(List.of(sampleLegajoCodigoImputacion()));

        mockMvc.perform(get("/api/haberes/core/legajocodigoimputacion/legajo/{legajoId}/{anho}/{mes}", 100L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "legajoCodigoImputacionId": 1,
                            "legajoId": 100,
                            "anho": 2024,
                            "mes": 6,
                            "dependenciaId": 7,
                            "facultadId": 3,
                            "geograficaId": 4,
                            "codigoId": 55,
                            "cuentaSueldos": 10.10,
                            "importe": 20.20,
                            "cuentaAportes": 30.30,
                            "created": null,
                            "updated": null
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }
}
