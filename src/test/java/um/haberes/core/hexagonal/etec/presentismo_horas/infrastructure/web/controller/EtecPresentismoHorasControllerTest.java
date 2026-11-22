package um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import um.haberes.core.hexagonal.etec.presentismo_horas.application.exception.EtecPresentismoHorasConflictoException;
import um.haberes.core.hexagonal.etec.presentismo_horas.application.service.EtecPresentismoHorasService;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.model.EtecPresentismoHoras;
import um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.web.mapper.EtecPresentismoHorasDtoMapper;

@ExtendWith(MockitoExtension.class)
class EtecPresentismoHorasControllerTest {

    @Mock
    private EtecPresentismoHorasService service;

    private MockMvc mockMvc;

    private EtecPresentismoHoras presentismo() {
        return EtecPresentismoHoras.builder()
                .presentismoHorasId(7L)
                .legajoId(100L)
                .anho(2026)
                .mes(11)
                .motivo("Ausencia injustificada")
                .build();
    }

    @BeforeEach
    void setUp() {
        EtecPresentismoHorasController controller = new EtecPresentismoHorasController(
                service, new EtecPresentismoHorasDtoMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void devuelveElEstadoDelLegajoAunqueNoHayaPerdidaRegistrada() throws Exception {
        when(service.findByLegajoAndPeriodo(100L, 2026, 11)).thenReturn(Optional.empty());
        when(service.tieneCargosHoras(100L, 2026, 11)).thenReturn(true);

        mockMvc.perform(get("/api/haberes/core/etec/presentismo-horas/legajo/100/periodo/2026/11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.presentismoHorasId").doesNotExist())
                .andExpect(jsonPath("$.tieneCargosHoras").value(true));
    }

    @Test
    void creaLaPerdidaYDevuelve201() throws Exception {
        when(service.add(any(EtecPresentismoHoras.class))).thenReturn(presentismo());

        mockMvc.perform(post("/api/haberes/core/etec/presentismo-horas/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "legajoId": 100,
                                  "anho": 2026,
                                  "mes": 11,
                                  "motivo": "Ausencia injustificada"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.presentismoHorasId").value(7));
    }

    @Test
    void unPeriodoAcreditadoResponde409() throws Exception {
        when(service.add(any(EtecPresentismoHoras.class)))
                .thenThrow(new EtecPresentismoHorasConflictoException("El período está acreditado"));

        mockMvc.perform(post("/api/haberes/core/etec/presentismo-horas/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "legajoId": 100,
                                  "anho": 2026,
                                  "mes": 11,
                                  "motivo": "Ausencia injustificada"
                                }
                                """))
                .andExpect(status().isConflict());
    }
}
