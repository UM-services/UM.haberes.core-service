package um.haberes.core.hexagonal.liquidaciones.general.application.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.CargoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.model.CargoLiquidacionView;
import um.haberes.core.hexagonal.crossslice.legajo_control.LegajoControlCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.liquidacion_proceso.ProcesoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.novedad.NovedadCrossSlice;
import um.haberes.core.hexagonal.crossslice.persona.PersonaCrossSlice;
import um.haberes.core.hexagonal.crossslice.persona.model.PersonaView;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.EstadoLegajoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.EstadoProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.MotivoResultadoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ResultadoLegajoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.TipoProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.ports.out.ProcesoGeneralRepository;
import um.haberes.core.hexagonal.liquidaciones.reglas.Inelegibilidad;
import um.haberes.core.hexagonal.liquidaciones.reglas.MotivoInelegibilidad;
import um.haberes.core.hexagonal.liquidaciones.reglas.ReglasElegibilidadLiquidacion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcesoGeneralRunnerTest {

    private static final String PROCESO_ID = "proceso-1";

    private static final Integer ANHO = 2026;

    private static final Integer MES = 11;

    private static final Long LEGAJO = 1001L;

    @Mock
    private ProcesoGeneralRepository procesoGeneralRepository;

    @Mock
    private PersonaCrossSlice personaCrossSlice;

    @Mock
    private LiquidacionCrossSlice liquidacionCrossSlice;

    @Mock
    private LegajoControlCrossSlice legajoControlCrossSlice;

    @Mock
    private ReglasElegibilidadLiquidacion reglas;

    @Mock
    private ProcesoLiquidacionCrossSlice procesoLiquidacionCrossSlice;

    @Mock
    private CargoLiquidacionCrossSlice cargoLiquidacionCrossSlice;

    private ProcesoGeneralRunner runner;

    @BeforeEach
    void setUp() {
        runner = new ProcesoGeneralRunner(procesoGeneralRepository, personaCrossSlice, liquidacionCrossSlice,
                legajoControlCrossSlice, reglas, procesoLiquidacionCrossSlice, cargoLiquidacionCrossSlice);
        ReflectionTestUtils.setField(runner, "concurrencia", 1);
    }

    @Test
    void liquidarOmiteLegajoBloqueado() {
        dadoProceso(TipoProcesoGeneral.LIQUIDAR, false);
        when(reglas.bloqueo(any(), anyBoolean()))
                .thenReturn(Optional.of(Inelegibilidad.de(MotivoInelegibilidad.BLOQUEADA,
                        ReglasElegibilidadLiquidacion.MOTIVO_BLOQUEO)));

        runner.ejecutar(PROCESO_ID);

        verify(procesoLiquidacionCrossSlice, never()).liquidar(any(), any(), any(), anyBoolean());
        ResultadoLegajoGeneral detalle = detalleGuardado();
        assertThat(detalle.resultado()).isEqualTo(EstadoLegajoGeneral.OMITIDO);
        assertThat(detalle.motivo()).isEqualTo(MotivoResultadoGeneral.BLOQUEADA);
        assertThat(procesoFinal()).extracting(ProcesoGeneral::estado, ProcesoGeneral::omitidos,
                ProcesoGeneral::procesados).containsExactly(EstadoProcesoGeneral.COMPLETADO, 1, 1);
    }

    @Test
    void liquidarDesmarcaPeriodoYRegistraLiquidado() {
        dadoProceso(TipoProcesoGeneral.LIQUIDAR, false);
        when(reglas.bloqueo(any(), anyBoolean())).thenReturn(Optional.empty());
        when(reglas.hayControl(ANHO, MES)).thenReturn(true);
        when(reglas.validarPersona(LEGAJO, ANHO, MES)).thenReturn(Optional.empty());
        when(reglas.novedades(LEGAJO, ANHO, MES)).thenReturn(List.of());
        when(reglas.periodoLiquidable(LEGAJO, ANHO, MES, List.of())).thenReturn(true);
        when(liquidacionCrossSlice.porClaveUnica(LEGAJO, ANHO, MES))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(liquidacion(new BigDecimal("1234.56"))));

        runner.ejecutar(PROCESO_ID);

        verify(legajoControlCrossSlice).desmarcarPeriodo(ANHO, MES);
        verify(procesoLiquidacionCrossSlice).liquidar(LEGAJO, ANHO, MES, false);
        ResultadoLegajoGeneral detalle = detalleGuardado();
        assertThat(detalle.resultado()).isEqualTo(EstadoLegajoGeneral.LIQUIDADO);
        assertThat(detalle.totalNeto()).isEqualByComparingTo("1234.56");
        assertThat(procesoFinal().exitosos()).isEqualTo(1);
    }

    @Test
    void cargosOmiteBloqueadoSinForzar() {
        dadoProceso(TipoProcesoGeneral.CARGOS, true);
        when(reglas.bloqueo(any(), eq(false)))
                .thenReturn(Optional.of(Inelegibilidad.de(MotivoInelegibilidad.BLOQUEADA, "bloqueada")));

        runner.ejecutar(PROCESO_ID);

        verify(procesoLiquidacionCrossSlice, never()).generarCargos(any(), any(), any());
        assertThat(detalleGuardado().resultado()).isEqualTo(EstadoLegajoGeneral.OMITIDO);
    }

    @Test
    void cargosGeneraYCuentaDocentesYNoDocentes() {
        dadoProceso(TipoProcesoGeneral.CARGOS, false);
        when(reglas.bloqueo(any(), eq(false))).thenReturn(Optional.empty());
        when(cargoLiquidacionCrossSlice.porLegajo(LEGAJO, ANHO, MES)).thenReturn(List.of(
                cargo(true), cargo(false), cargo(false)));

        runner.ejecutar(PROCESO_ID);

        verify(procesoLiquidacionCrossSlice).generarCargos(LEGAJO, ANHO, MES);
        ResultadoLegajoGeneral detalle = detalleGuardado();
        assertThat(detalle.resultado()).isEqualTo(EstadoLegajoGeneral.GENERADO);
        assertThat(detalle.detalle()).contains("1 docente(s) y 2 no docente(s)");
    }

    private void dadoProceso(TipoProcesoGeneral tipo, boolean forzar) {
        ProcesoGeneral proceso = new ProcesoGeneral(PROCESO_ID, tipo, ANHO, MES,
                EstadoProcesoGeneral.EN_CURSO, 1, 0, 0, 0, 0, forzar, 99L, OffsetDateTime.now(), null, null);
        when(procesoGeneralRepository.findById(PROCESO_ID)).thenReturn(Optional.of(proceso));
        when(personaCrossSlice.liquidables()).thenReturn(List.of(persona()));
    }

    private PersonaView persona() {
        return new PersonaView(LEGAJO, new BigDecimal("12345678"), "PEREZ", "JUAN", 1, "S", 1, "S");
    }

    private LiquidacionView liquidacion(BigDecimal neto) {
        return new LiquidacionView(1L, LEGAJO, ANHO, MES, (byte) 0, 1, "S", neto, BigDecimal.ZERO,
                BigDecimal.ZERO, neto, OffsetDateTime.now(), null, 1, "S");
    }

    private CargoLiquidacionView cargo(boolean docente) {
        return new CargoLiquidacionView(1L, LEGAJO, 1, "FING", OffsetDateTime.now(), OffsetDateTime.now(),
                1, "PROFESOR", BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.TEN, 1, 0, "A", docente);
    }

    private ResultadoLegajoGeneral detalleGuardado() {
        ArgumentCaptor<ResultadoLegajoGeneral> captor = ArgumentCaptor.forClass(ResultadoLegajoGeneral.class);
        verify(procesoGeneralRepository).saveDetalle(eq(PROCESO_ID), captor.capture());
        return captor.getValue();
    }

    private ProcesoGeneral procesoFinal() {
        ArgumentCaptor<ProcesoGeneral> captor = ArgumentCaptor.forClass(ProcesoGeneral.class);
        verify(procesoGeneralRepository, org.mockito.Mockito.atLeastOnce()).save(captor.capture());
        return captor.getValue();
    }
}
