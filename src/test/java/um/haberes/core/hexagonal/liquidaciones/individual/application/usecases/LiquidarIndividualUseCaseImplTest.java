package um.haberes.core.hexagonal.liquidaciones.individual.application.usecases;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.liquidacion_proceso.ProcesoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.novedad.NovedadCrossSlice;
import um.haberes.core.hexagonal.crossslice.novedad.model.NovedadView;
import um.haberes.core.hexagonal.liquidaciones.individual.application.service.LiquidacionIndividualReglas;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.EstadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResultadoProcesoIndividual;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LiquidarIndividualUseCaseImplTest {

    private static final Long LEGAJO = 1001L;

    private static final Integer ANHO = 2026;

    private static final Integer MES = 11;

    @Mock
    private LiquidacionCrossSlice liquidacionCrossSlice;

    @Mock
    private NovedadCrossSlice novedadCrossSlice;

    @Mock
    private ProcesoLiquidacionCrossSlice procesoLiquidacionCrossSlice;

    @Mock
    private LiquidacionIndividualReglas reglas;

    @InjectMocks
    private LiquidarIndividualUseCaseImpl useCase;

    @Test
    void bloqueadaNoInvocaElMotor() {
        when(reglas.bloquearSiCorresponde(any(), anyBoolean())).thenReturn(Optional.of(
                ResultadoProcesoIndividual.de(EstadoProcesoIndividual.BLOQUEADA, "bloqueada")));

        ResultadoProcesoIndividual resultado = useCase.liquidar(LEGAJO, ANHO, MES, false);

        assertThat(resultado.resultado()).isEqualTo(EstadoProcesoIndividual.BLOQUEADA);
        verify(procesoLiquidacionCrossSlice, never()).liquidar(anyLong(), anyInt(), anyInt(), anyBoolean());
    }

    @Test
    void sinControlNoInvocaElMotor() {
        when(reglas.bloquearSiCorresponde(any(), anyBoolean())).thenReturn(Optional.empty());
        when(reglas.hayControl(ANHO, MES)).thenReturn(false);

        ResultadoProcesoIndividual resultado = useCase.liquidar(LEGAJO, ANHO, MES, false);

        assertThat(resultado.resultado()).isEqualTo(EstadoProcesoIndividual.SIN_CONTROL);
        verify(procesoLiquidacionCrossSlice, never()).liquidar(anyLong(), anyInt(), anyInt(), anyBoolean());
    }

    @Test
    void personaNoLiquidableCortaElProceso() {
        when(reglas.bloquearSiCorresponde(any(), anyBoolean())).thenReturn(Optional.empty());
        when(reglas.hayControl(ANHO, MES)).thenReturn(true);
        when(reglas.validarPersona(LEGAJO, ANHO, MES)).thenReturn(Optional.of(
                ResultadoProcesoIndividual.de(EstadoProcesoIndividual.PERSONA_NO_LIQUIDABLE, "no liquidable")));

        ResultadoProcesoIndividual resultado = useCase.liquidar(LEGAJO, ANHO, MES, false);

        assertThat(resultado.resultado()).isEqualTo(EstadoProcesoIndividual.PERSONA_NO_LIQUIDABLE);
        verify(procesoLiquidacionCrossSlice, never()).liquidar(anyLong(), anyInt(), anyInt(), anyBoolean());
    }

    @Test
    void periodoNoLiquidableNoInvocaElMotor() {
        when(reglas.bloquearSiCorresponde(any(), anyBoolean())).thenReturn(Optional.empty());
        when(reglas.hayControl(ANHO, MES)).thenReturn(true);
        when(reglas.validarPersona(LEGAJO, ANHO, MES)).thenReturn(Optional.empty());
        when(novedadCrossSlice.porLegajo(LEGAJO, ANHO, MES)).thenReturn(List.of());
        when(reglas.periodoLiquidable(LEGAJO, ANHO, MES, List.of())).thenReturn(false);

        ResultadoProcesoIndividual resultado = useCase.liquidar(LEGAJO, ANHO, MES, false);

        assertThat(resultado.resultado()).isEqualTo(EstadoProcesoIndividual.PERIODO_NO_LIQUIDABLE);
        verify(procesoLiquidacionCrossSlice, never()).liquidar(anyLong(), anyInt(), anyInt(), anyBoolean());
    }

    @Test
    void motorSinLiquidacionResultanteInformaNoLiquidable() {
        cuandoPasaLosPreChequeos();
        when(liquidacionCrossSlice.porClaveUnica(LEGAJO, ANHO, MES)).thenReturn(Optional.empty());

        ResultadoProcesoIndividual resultado = useCase.liquidar(LEGAJO, ANHO, MES, true);

        assertThat(resultado.resultado()).isEqualTo(EstadoProcesoIndividual.NO_LIQUIDABLE);
        verify(procesoLiquidacionCrossSlice).liquidar(LEGAJO, ANHO, MES, true);
    }

    @Test
    void liquidacionExitosaDevuelveLiquidado() {
        cuandoPasaLosPreChequeos();
        when(liquidacionCrossSlice.porClaveUnica(LEGAJO, ANHO, MES)).thenReturn(Optional.of(liquidacion()));

        ResultadoProcesoIndividual resultado = useCase.liquidar(LEGAJO, ANHO, MES, false);

        assertThat(resultado.resultado()).isEqualTo(EstadoProcesoIndividual.LIQUIDADO);
    }

    private void cuandoPasaLosPreChequeos() {
        when(reglas.bloquearSiCorresponde(any(), anyBoolean())).thenReturn(Optional.empty());
        when(reglas.hayControl(ANHO, MES)).thenReturn(true);
        when(reglas.validarPersona(LEGAJO, ANHO, MES)).thenReturn(Optional.empty());
        when(novedadCrossSlice.porLegajo(LEGAJO, ANHO, MES)).thenReturn(List.of());
        when(reglas.periodoLiquidable(LEGAJO, ANHO, MES, List.of())).thenReturn(true);
    }

    private LiquidacionView liquidacion() {
        return new LiquidacionView(1L, ANHO, MES, (byte) 0, 1, "S", BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO, null, null, 7, "B");
    }

    @SuppressWarnings("unused")
    private NovedadView novedad() {
        return new NovedadView(1L, 16, "PRESENTISMO", BigDecimal.ZERO, "N", null, null);
    }
}
