package um.haberes.core.hexagonal.liquidaciones.individual.application.usecases;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.liquidacion_proceso.ProcesoLiquidacionCrossSlice;
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
class EliminarLiquidacionIndividualUseCaseImplTest {

    private static final Long LEGAJO = 1001L;

    private static final Integer ANHO = 2026;

    private static final Integer MES = 11;

    @Mock
    private LiquidacionCrossSlice liquidacionCrossSlice;

    @Mock
    private ProcesoLiquidacionCrossSlice procesoLiquidacionCrossSlice;

    @Mock
    private LiquidacionIndividualReglas reglas;

    @InjectMocks
    private EliminarLiquidacionIndividualUseCaseImpl useCase;

    @Test
    void sinLiquidacionNoInvocaElMotor() {
        when(liquidacionCrossSlice.porClaveUnica(LEGAJO, ANHO, MES)).thenReturn(Optional.empty());

        ResultadoProcesoIndividual resultado = useCase.eliminar(LEGAJO, ANHO, MES, false);

        assertThat(resultado.resultado()).isEqualTo(EstadoProcesoIndividual.SIN_LIQUIDACION);
        verify(procesoLiquidacionCrossSlice, never())
                .eliminarLiquidacion(anyLong(), anyInt(), anyInt(), anyBoolean());
    }

    @Test
    void bloqueadaNoElimina() {
        when(liquidacionCrossSlice.porClaveUnica(LEGAJO, ANHO, MES))
                .thenReturn(Optional.of(liquidacion((byte) 1)));
        when(reglas.bloquearSiCorresponde(any(), anyBoolean())).thenReturn(Optional.of(
                ResultadoProcesoIndividual.de(EstadoProcesoIndividual.BLOQUEADA, "bloqueada")));

        ResultadoProcesoIndividual resultado = useCase.eliminar(LEGAJO, ANHO, MES, false);

        assertThat(resultado.resultado()).isEqualTo(EstadoProcesoIndividual.BLOQUEADA);
        verify(procesoLiquidacionCrossSlice, never())
                .eliminarLiquidacion(anyLong(), anyInt(), anyInt(), anyBoolean());
    }

    @Test
    void eliminacionExitosaDevuelveEliminada() {
        when(liquidacionCrossSlice.porClaveUnica(LEGAJO, ANHO, MES))
                .thenReturn(Optional.of(liquidacion((byte) 0)), Optional.empty());
        when(reglas.bloquearSiCorresponde(any(), anyBoolean())).thenReturn(Optional.empty());

        ResultadoProcesoIndividual resultado = useCase.eliminar(LEGAJO, ANHO, MES, true);

        assertThat(resultado.resultado()).isEqualTo(EstadoProcesoIndividual.ELIMINADA);
        verify(procesoLiquidacionCrossSlice).eliminarLiquidacion(LEGAJO, ANHO, MES, true);
    }

    @Test
    void siLaLiquidacionSigueExistiendoInformaNoEliminada() {
        when(liquidacionCrossSlice.porClaveUnica(LEGAJO, ANHO, MES))
                .thenReturn(Optional.of(liquidacion((byte) 0)));
        when(reglas.bloquearSiCorresponde(any(), anyBoolean())).thenReturn(Optional.empty());

        ResultadoProcesoIndividual resultado = useCase.eliminar(LEGAJO, ANHO, MES, false);

        assertThat(resultado.resultado()).isEqualTo(EstadoProcesoIndividual.NO_ELIMINADA);
    }

    private LiquidacionView liquidacion(byte bloqueado) {
        return new LiquidacionView(1L, ANHO, MES, bloqueado, 1, "S", BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO, null, null, 7, "B");
    }
}
