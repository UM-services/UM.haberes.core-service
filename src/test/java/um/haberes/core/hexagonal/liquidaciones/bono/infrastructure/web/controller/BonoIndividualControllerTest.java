package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import um.haberes.core.hexagonal.liquidaciones.bono.application.exception.BonoException;
import um.haberes.core.hexagonal.liquidaciones.bono.application.service.BonoService;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.Actividad;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.AuditoriaBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoImpresion;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.EnvioBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.FaltanteBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.IntegridadBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.PeriodoBono;
import um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.mapper.BonoDtoMapper;
import um.haberes.core.util.ClientIpResolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BonoIndividualControllerTest {

    @Mock
    private BonoService bonoService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        BonoIndividualController controller = new BonoIndividualController(bonoService, new BonoDtoMapper(),
                new ClientIpResolver());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void integridadDevuelve200ConFaltantes() throws Exception {
        when(bonoService.verificarIntegridad(any(PeriodoBono.class))).thenReturn(IntegridadBono.builder()
                .legajoId(12345L).anho(2026).mes(8).ok(false)
                .faltantes(List.of(FaltanteBono.ITEM, FaltanteBono.LEGAJO_CONTROL))
                .build());

        mockMvc.perform(get("/api/haberes/core/bono/12345/2026/8/integridad"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.legajoId").value(12345))
                .andExpect(jsonPath("$.anho").value(2026))
                .andExpect(jsonPath("$.mes").value(8))
                .andExpect(jsonPath("$.ok").value(false))
                .andExpect(jsonPath("$.faltantes[0]").value("ITEM"))
                .andExpect(jsonPath("$.faltantes[1]").value("LEGAJO_CONTROL"));
    }

    @Test
    void prepareDevuelveActividadRecalculada() throws Exception {
        when(bonoService.preparar(any(PeriodoBono.class))).thenReturn(Actividad.builder()
                .actividadId(5L).legajoId(12345L).anho(2026).mes(8)
                .docente((byte) 1).otras((byte) 0).clases((byte) 1)
                .build());

        mockMvc.perform(post("/api/haberes/core/bono/12345/2026/8/prepare"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actividadId").value(5))
                .andExpect(jsonPath("$.docente").value(1))
                .andExpect(jsonPath("$.otras").value(0))
                .andExpect(jsonPath("$.clases").value(1));
    }

    @Test
    void prepareSinLiquidacionDevuelve400() throws Exception {
        when(bonoService.preparar(any(PeriodoBono.class)))
                .thenThrow(new BonoException("Cannot prepare Bono: falta LIQUIDACION"));

        mockMvc.perform(post("/api/haberes/core/bono/12345/2026/8/prepare"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void auditoriaUsaHeaderSolicitanteEIpDerivada() throws Exception {
        when(bonoService.registrarAuditoria(any(AuditoriaBono.class)))
                .thenReturn(BonoImpresion.builder().bonoImpresionId(77L).legajoId(12345L).anho(2026).mes(8)
                        .legajoIdSolicitud(999L).ipAddress("10.0.0.5").build());

        mockMvc.perform(post("/api/haberes/core/bono/12345/2026/8/auditoria")
                        .header("X-Legajo-Solicitante", "999")
                        .header("X-Forwarded-For", "10.0.0.5, 192.168.1.1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bonoImpresionId").value(77))
                .andExpect(jsonPath("$.legajoIdSolicitud").value(999))
                .andExpect(jsonPath("$.ipAddress").value("10.0.0.5"));

        ArgumentCaptor<AuditoriaBono> captor = ArgumentCaptor.forClass(AuditoriaBono.class);
        verify(bonoService).registrarAuditoria(captor.capture());
        assertThat(captor.getValue().getLegajoIdSolicitud()).isEqualTo(999L);
        assertThat(captor.getValue().getIpAddress()).isEqualTo("10.0.0.5");
    }

    @Test
    void auditoriaUsaBodyCuandoNoHayHeader() throws Exception {
        when(bonoService.registrarAuditoria(any(AuditoriaBono.class)))
                .thenReturn(BonoImpresion.builder().bonoImpresionId(78L).build());

        mockMvc.perform(post("/api/haberes/core/bono/12345/2026/8/auditoria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"legajoIdSolicitud\": 555}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bonoImpresionId").value(78));

        ArgumentCaptor<AuditoriaBono> captor = ArgumentCaptor.forClass(AuditoriaBono.class);
        verify(bonoService).registrarAuditoria(captor.capture());
        assertThat(captor.getValue().getLegajoIdSolicitud()).isEqualTo(555L);
    }

    @Test
    void auditoriaSinSolicitanteAlgunoDevuelve400() throws Exception {
        when(bonoService.registrarAuditoria(any(AuditoriaBono.class)))
                .thenThrow(new BonoException("legajoIdSolicitud is required"));

        mockMvc.perform(post("/api/haberes/core/bono/12345/2026/8/auditoria"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void printPrepareDevuelve400ConFaltantes() throws Exception {
        when(bonoService.printPrepare(any(AuditoriaBono.class)))
                .thenThrow(new BonoException("integridad fallida", List.of(FaltanteBono.ITEM)));

        mockMvc.perform(post("/api/haberes/core/bono/12345/2026/8/print-prepare")
                        .header("X-Legajo-Solicitante", "999"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.ok").value(false))
                .andExpect(jsonPath("$.faltantes[0]").value("ITEM"));
    }

    @Test
    void printPrepareOkDevuelveAuditoria() throws Exception {
        when(bonoService.printPrepare(any(AuditoriaBono.class)))
                .thenReturn(BonoImpresion.builder().bonoImpresionId(79L).build());

        mockMvc.perform(post("/api/haberes/core/bono/12345/2026/8/print-prepare")
                        .header("X-Legajo-Solicitante", "999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bonoImpresionId").value(79));
    }

    @Test
    void sendPrepareValidaMailUpsertYAudita() throws Exception {
        when(bonoService.prepararEnvio(any(EnvioBono.class)))
                .thenReturn(BonoImpresion.builder().bonoImpresionId(80L).build());

        mockMvc.perform(post("/api/haberes/core/bono/12345/2026/8/send-prepare")
                        .header("X-Legajo-Solicitante", "999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                java.util.Map.of("mailInstitucional", "agente@um.edu.ar"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bonoImpresionId").value(80));

        ArgumentCaptor<EnvioBono> captor = ArgumentCaptor.forClass(EnvioBono.class);
        verify(bonoService).prepararEnvio(captor.capture());
        assertThat(captor.getValue().getMailInstitucional()).isEqualTo("agente@um.edu.ar");
        assertThat(captor.getValue().getLegajoId()).isEqualTo(12345L);
        assertThat(captor.getValue().getLegajoIdSolicitud()).isEqualTo(999L);
    }

    @Test
    void sendPrepareConMailVacioDevuelve400() throws Exception {
        mockMvc.perform(post("/api/haberes/core/bono/12345/2026/8/send-prepare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mailInstitucional\": \"\"}"))
                .andExpect(status().isBadRequest());
        verify(bonoService, never()).prepararEnvio(any());
    }

    @Test
    void historialAuditoriaDevuelveLista() throws Exception {
        when(bonoService.getHistorial(any(PeriodoBono.class))).thenReturn(List.of(
                BonoImpresion.builder().bonoImpresionId(1L).legajoId(12345L).anho(2026).mes(8).build()));

        mockMvc.perform(get("/api/haberes/core/bono/12345/2026/8/auditoria"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bonoImpresionId").value(1));
    }
}
