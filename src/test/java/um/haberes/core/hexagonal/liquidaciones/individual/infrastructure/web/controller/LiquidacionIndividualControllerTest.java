package um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.controller;

import java.math.BigDecimal;
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

import um.haberes.core.hexagonal.crossslice.antiguedad.model.AntiguedadView;
import um.haberes.core.hexagonal.crossslice.control.model.ControlView;
import um.haberes.core.hexagonal.crossslice.novedad.model.NovedadView;
import um.haberes.core.hexagonal.crossslice.persona.model.PersonaView;
import um.haberes.core.hexagonal.liquidaciones.individual.application.service.LiquidacionIndividualService;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.AccionesLiquidacionIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.EstadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.FichaLiquidacionIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.NovedadRegistradaIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResumenGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResultadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.mapper.LiquidacionIndividualDtoMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LiquidacionIndividualControllerTest {

    private static final Long LEGAJO = 1001L;

    private static final Integer ANHO = 2026;

    private static final Integer MES = 11;

    @Mock
    private LiquidacionIndividualService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        LiquidacionIndividualController controller = new LiquidacionIndividualController(service,
                new LiquidacionIndividualDtoMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void fichaDevuelve200ConAcciones() throws Exception {
        when(service.obtenerFicha(LEGAJO, ANHO, MES)).thenReturn(Optional.of(ficha()));

        mockMvc.perform(get("/api/haberes/core/liquidacion-individual/{legajoId}/{anho}/{mes}",
                        LEGAJO, ANHO, MES))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.legajoId").value(LEGAJO))
                .andExpect(jsonPath("$.persona.apellido").value("PEREZ"))
                .andExpect(jsonPath("$.acciones.puedeLiquidar").value(true))
                .andExpect(jsonPath("$.acciones.bloqueado").value(false))
                .andExpect(jsonPath("$.novedades[0].codigoNombre").value("PRESENTISMO"));
    }

    @Test
    void fichaDeLegajoInexistenteDevuelve404() throws Exception {
        when(service.obtenerFicha(LEGAJO, ANHO, MES)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/haberes/core/liquidacion-individual/{legajoId}/{anho}/{mes}",
                        LEGAJO, ANHO, MES))
                .andExpect(status().isNotFound());
    }

    @Test
    void liquidarDevuelveResultadoTipado() throws Exception {
        when(service.liquidar(LEGAJO, ANHO, MES, false)).thenReturn(
                ResultadoProcesoIndividual.de(EstadoProcesoIndividual.LIQUIDADO, "ok"));

        mockMvc.perform(post("/api/haberes/core/liquidacion-individual/{legajoId}/{anho}/{mes}/liquidar",
                        LEGAJO, ANHO, MES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"forzar\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultado").value("LIQUIDADO"))
                .andExpect(jsonPath("$.mensaje").value("ok"));
    }

    @Test
    void generarCargosDevuelveConteos() throws Exception {
        when(service.generarCargos(LEGAJO, ANHO, MES)).thenReturn(
                new ResumenGeneracionCargos(EstadoProcesoIndividual.GENERADOS, "ok", 2, 1));

        mockMvc.perform(post("/api/haberes/core/liquidacion-individual/{legajoId}/{anho}/{mes}/cargos",
                        LEGAJO, ANHO, MES))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultado").value("GENERADOS"))
                .andExpect(jsonPath("$.docentes").value(2))
                .andExpect(jsonPath("$.noDocentes").value(1));
    }

    @Test
    void registrarNovedadDevuelveLaNovedad() throws Exception {
        when(service.registrarNovedad(anyLong(), anyInt(), anyInt(), any())).thenReturn(
                new NovedadRegistradaIndividual(EstadoProcesoIndividual.GUARDADA, "ok",
                        new NovedadView(77L, 16, "PRESENTISMO", BigDecimal.ZERO, "N", null, null)));

        mockMvc.perform(post("/api/haberes/core/liquidacion-individual/{legajoId}/{anho}/{mes}/novedad",
                        LEGAJO, ANHO, MES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codigoId\":16,\"valor\":\"N\",\"forzar\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultado").value("GUARDADA"))
                .andExpect(jsonPath("$.novedad.novedadId").value(77));
    }

    @Test
    void eliminarLiquidacionUsaElForzarDelPath() throws Exception {
        when(service.eliminarLiquidacion(LEGAJO, ANHO, MES, true)).thenReturn(
                ResultadoProcesoIndividual.de(EstadoProcesoIndividual.ELIMINADA, "ok"));

        mockMvc.perform(delete("/api/haberes/core/liquidacion-individual/{legajoId}/{anho}/{mes}/{forzar}",
                        LEGAJO, ANHO, MES, true))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultado").value("ELIMINADA"));
    }

    @Test
    void eliminarNovedadUsaElForzarDelPath() throws Exception {
        when(service.eliminarNovedad(LEGAJO, ANHO, MES, 77L, false)).thenReturn(
                ResultadoProcesoIndividual.de(EstadoProcesoIndividual.ELIMINADA, "ok"));

        mockMvc.perform(delete(
                        "/api/haberes/core/liquidacion-individual/{legajoId}/{anho}/{mes}/novedad/{novedadId}/{forzar}",
                        LEGAJO, ANHO, MES, 77L, false))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultado").value("ELIMINADA"));
    }

    private FichaLiquidacionIndividual ficha() {
        return new FichaLiquidacionIndividual(LEGAJO, ANHO, MES,
                new PersonaView(LEGAJO, BigDecimal.TEN, "PEREZ", "ANA", 1, "S", 7, "B"),
                new ControlView(88L, ANHO, MES, 1),
                null,
                new AntiguedadView(192, 0, new BigDecimal("1.85"), BigDecimal.ONE),
                List.of(), List.of(), List.of(),
                List.of(new NovedadView(77L, 16, "PRESENTISMO", BigDecimal.ZERO, "N", null, null)),
                List.of(),
                new AccionesLiquidacionIndividual(true, true, false, true, false, null));
    }
}
