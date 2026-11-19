package um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.controller;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
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

import um.haberes.core.hexagonal.liquidaciones.general.application.service.LiquidacionGeneralService;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.AccionesLiquidacionGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.EstadoLegajoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.EstadoProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.FilaLiquidacionGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.InicioProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.PadronLiquidacionGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ResultadoLegajoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ResumenPadron;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.TipoProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.mapper.LiquidacionGeneralDtoMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LiquidacionGeneralControllerTest {

    private static final Integer ANHO = 2026;

    private static final Integer MES = 11;

    private static final String PROCESO_ID = "proceso-1";

    @Mock
    private LiquidacionGeneralService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                new LiquidacionGeneralController(service, new LiquidacionGeneralDtoMapper())).build();
    }

    @Test
    void padronDevuelveResumenYFilas() throws Exception {
        when(service.obtener(ANHO, MES)).thenReturn(padron());

        mockMvc.perform(get("/api/haberes/core/liquidacion-general/{anho}/{mes}", ANHO, MES))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resumen.total").value(1))
                .andExpect(jsonPath("$.filas[0].legajoId").value(1001))
                .andExpect(jsonPath("$.filas[0].bloqueado").value(true))
                .andExpect(jsonPath("$.acciones.puedeLiquidar").value(false));
    }

    @Test
    void iniciarLiquidarDevuelve202() throws Exception {
        when(service.iniciar(ANHO, MES, true, 99L))
                .thenReturn(InicioProcesoGeneral.iniciado(proceso(EstadoProcesoGeneral.EN_CURSO)));

        mockMvc.perform(post("/api/haberes/core/liquidacion-general/{anho}/{mes}/liquidar", ANHO, MES)
                        .header("X-Legajo-Solicitante", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"forzar\":true}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.iniciado").value(true))
                .andExpect(jsonPath("$.proceso.procesoId").value(PROCESO_ID));
    }

    @Test
    void iniciarConProcesoEnCursoDevuelve409() throws Exception {
        when(service.iniciar(ANHO, MES, 99L)).thenReturn(
                InicioProcesoGeneral.noIniciado(LiquidacionGeneralService.PROCESO_EN_CURSO, "en curso"));

        mockMvc.perform(post("/api/haberes/core/liquidacion-general/{anho}/{mes}/cargos", ANHO, MES)
                        .header("X-Legajo-Solicitante", 99L))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.motivo").value(LiquidacionGeneralService.PROCESO_EN_CURSO));
    }

    @Test
    void iniciarSinControlDevuelve200ConMotivo() throws Exception {
        when(service.iniciar(ANHO, MES, 99L)).thenReturn(
                InicioProcesoGeneral.noIniciado(LiquidacionGeneralService.SIN_CONTROL, "sin control"));

        mockMvc.perform(post("/api/haberes/core/liquidacion-general/{anho}/{mes}/cargos", ANHO, MES)
                        .header("X-Legajo-Solicitante", 99L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.iniciado").value(false))
                .andExpect(jsonPath("$.motivo").value(LiquidacionGeneralService.SIN_CONTROL));
    }

    @Test
    void procesoInexistenteDevuelve404() throws Exception {
        when(service.obtener(PROCESO_ID)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/haberes/core/liquidacion-general/proceso/{procesoId}", PROCESO_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void detalleDevuelveResultadosPorEstado() throws Exception {
        when(service.obtener(PROCESO_ID)).thenReturn(Optional.of(proceso(EstadoProcesoGeneral.COMPLETADO)));
        when(service.obtener(PROCESO_ID, EstadoLegajoGeneral.OMITIDO)).thenReturn(List.of(
                new ResultadoLegajoGeneral(1L, 1001L, "PEREZ", "JUAN", EstadoLegajoGeneral.OMITIDO,
                        um.haberes.core.hexagonal.liquidaciones.general.domain.model.MotivoResultadoGeneral.BLOQUEADA,
                        "bloqueada", null, OffsetDateTime.now())));

        mockMvc.perform(get("/api/haberes/core/liquidacion-general/proceso/{procesoId}/detalle/{resultado}",
                        PROCESO_ID, "OMITIDO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].legajoId").value(1001))
                .andExpect(jsonPath("$[0].motivo").value("BLOQUEADA"));
    }

    private PadronLiquidacionGeneral padron() {
        return new PadronLiquidacionGeneral(ANHO, MES,
                new AccionesLiquidacionGeneral(false, false, false, true),
                new ResumenPadron(1, 1, 0, 1, 0, 1, 0, 0, BigDecimal.TEN),
                List.of(new FilaLiquidacionGeneral(1, 1001L, "PEREZ", "JUAN", 1, "S", 1, "S", true, false,
                        true, false, true, BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.TEN)));
    }

    private ProcesoGeneral proceso(EstadoProcesoGeneral estado) {
        return new ProcesoGeneral(PROCESO_ID, TipoProcesoGeneral.LIQUIDAR, ANHO, MES, estado, 10, 10, 9, 1, 0,
                true, 99L, OffsetDateTime.now(), OffsetDateTime.now(), "ok");
    }
}
