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
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.application.service.LegajoCargoClaseImputacionService;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.model.LegajoCargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.infrastructure.web.controller.LegajoCargoClaseImputacionController;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.infrastructure.web.dto.LegajoCargoClaseImputacionResponse;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.infrastructure.web.mapper.LegajoCargoClaseImputacionDtoMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LegajoCargoClaseImputacionControllerTest {

    @Mock
    private LegajoCargoClaseImputacionService service;

    @Mock
    private LegajoCargoClaseImputacionDtoMapper mapper;

    @InjectMocks
    private LegajoCargoClaseImputacionController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private LegajoCargoClaseImputacion sampleLegajoCargoClaseImputacion() {
        LegajoCargoClaseImputacion legajoCargoClaseImputacion = new LegajoCargoClaseImputacion();
        legajoCargoClaseImputacion.setLegajoCargoClaseImputacionId(11L);
        legajoCargoClaseImputacion.setLegajoId(123L);
        legajoCargoClaseImputacion.setAnho(2024);
        legajoCargoClaseImputacion.setMes(6);
        legajoCargoClaseImputacion.setDependenciaId(2);
        legajoCargoClaseImputacion.setFacultadId(3);
        legajoCargoClaseImputacion.setGeograficaId(4);
        legajoCargoClaseImputacion.setCargoClaseId(55L);
        legajoCargoClaseImputacion.setCuentaSueldos(new BigDecimal("30000"));
        legajoCargoClaseImputacion.setBasico(new BigDecimal("20000.00"));
        legajoCargoClaseImputacion.setAntiguedad(new BigDecimal("1500.50"));
        legajoCargoClaseImputacion.setCuentaAportes(new BigDecimal("5000"));
        return legajoCargoClaseImputacion;
    }

    private LegajoCargoClaseImputacionResponse sampleResponse() {
        return LegajoCargoClaseImputacionResponse.builder()
                .legajoCargoClaseImputacionId(11L)
                .legajoId(123L)
                .anho(2024)
                .mes(6)
                .dependenciaId(2)
                .facultadId(3)
                .geograficaId(4)
                .cargoClaseId(55L)
                .cuentaSueldos(new BigDecimal("30000"))
                .basico(new BigDecimal("20000.00"))
                .antiguedad(new BigDecimal("1500.50"))
                .cuentaAportes(new BigDecimal("5000"))
                .build();
    }

    private String legajoCargoClaseImputacionJson() {
        return """
                {
                  "legajoCargoClaseImputacionId": 11,
                  "legajoId": 123,
                  "anho": 2024,
                  "mes": 6,
                  "dependenciaId": 2,
                  "facultadId": 3,
                  "geograficaId": 4,
                  "cargoClaseId": 55,
                  "cuentaSueldos": 30000,
                  "basico": 20000.00,
                  "antiguedad": 1500.50,
                  "cuentaAportes": 5000
                }
                """;
    }

    @Test
    void findAllByLegajo_returnsOkWithListOfLegajoCargoClaseImputacions() throws Exception {
        when(service.findAllByLegajo(123L, 2024, 6)).thenReturn(List.of(sampleLegajoCargoClaseImputacion()));
        when(mapper.toResponse(any(LegajoCargoClaseImputacion.class))).thenReturn(sampleResponse());

        mockMvc.perform(get("/api/haberes/core/legajocargoclaseimputacion/legajo/{legajoId}/{anho}/{mes}", 123, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + legajoCargoClaseImputacionJson() + "]", JsonCompareMode.STRICT));
    }
}
