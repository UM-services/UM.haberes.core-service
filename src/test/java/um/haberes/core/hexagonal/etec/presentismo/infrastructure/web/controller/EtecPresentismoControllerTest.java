package um.haberes.core.hexagonal.etec.presentismo.infrastructure.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import um.haberes.core.hexagonal.etec.presentismo.application.exception.EtecPresentismoConflictoException;
import um.haberes.core.hexagonal.etec.presentismo.application.service.EtecPresentismoService;
import um.haberes.core.hexagonal.etec.presentismo.domain.model.EtecPresentismo;
import um.haberes.core.hexagonal.etec.presentismo.infrastructure.web.mapper.EtecPresentismoDtoMapper;

@ExtendWith(MockitoExtension.class)
class EtecPresentismoControllerTest {

    @Mock
    private EtecPresentismoService service;

    private MockMvc mockMvc;

    private EtecPresentismo presentismo() {
        return EtecPresentismo.builder()
                .presentismoId(1L)
                .asignacionCargoId(12L)
                .anho(2026)
                .mes(11)
                .motivo("Ausencia injustificada")
                .legajoId(100L)
                .categoriaId(992)
                .build();
    }

    @BeforeEach
    void setUp() {
        EtecPresentismoController controller = new EtecPresentismoController(
                service, new EtecPresentismoDtoMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listaLasPerdidasDelLegajoEnElPeriodo() throws Exception {
        when(service.findAllByLegajoAndPeriodo(100L, 2026, 11)).thenReturn(List.of(presentismo()));

        mockMvc.perform(get("/api/haberes/core/etec/presentismo/legajo/100/periodo/2026/11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].presentismoId").value(1))
                .andExpect(jsonPath("$[0].motivo").value("Ausencia injustificada"));
    }

    @Test
    void devuelve404CuandoLaAsignacionNoTienePerdidaEnElPeriodo() throws Exception {
        when(service.findByAsignacionAndPeriodo(12L, 2026, 11)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/haberes/core/etec/presentismo/asignacion/12/periodo/2026/11"))
                .andExpect(status().isNotFound());
    }

    @Test
    void creaLaPerdidaYDevuelve201() throws Exception {
        when(service.add(any(EtecPresentismo.class))).thenReturn(presentismo());

        mockMvc.perform(post("/api/haberes/core/etec/presentismo/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "asignacionCargoId": 12,
                                  "anho": 2026,
                                  "mes": 11,
                                  "motivo": "Ausencia injustificada"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.presentismoId").value(1));
    }

    @Test
    void unPeriodoAcreditadoResponde409() throws Exception {
        when(service.add(any(EtecPresentismo.class)))
                .thenThrow(new EtecPresentismoConflictoException("El período está acreditado"));

        mockMvc.perform(post("/api/haberes/core/etec/presentismo/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "asignacionCargoId": 12,
                                  "anho": 2026,
                                  "mes": 11,
                                  "motivo": "Ausencia injustificada"
                                }
                                """))
                .andExpect(status().isConflict());
    }
}
