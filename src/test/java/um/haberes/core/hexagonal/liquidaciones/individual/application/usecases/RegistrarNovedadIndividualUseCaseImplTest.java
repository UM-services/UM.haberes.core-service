package um.haberes.core.hexagonal.liquidaciones.individual.application.usecases;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.novedad.NovedadCrossSlice;
import um.haberes.core.hexagonal.crossslice.novedad.model.NovedadView;
import um.haberes.core.hexagonal.liquidaciones.individual.application.service.LiquidacionIndividualReglas;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.EstadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.NovedadRegistradaIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.RegistroNovedadIndividual;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrarNovedadIndividualUseCaseImplTest {

    private static final Long LEGAJO = 1001L;

    private static final Integer ANHO = 2026;

    private static final Integer MES = 11;

    @Mock
    private LiquidacionCrossSlice liquidacionCrossSlice;

    @Mock
    private NovedadCrossSlice novedadCrossSlice;

    @Mock
    private LiquidacionIndividualReglas reglas;

    @InjectMocks
    private RegistrarNovedadIndividualUseCaseImpl useCase;

    @Test
    void valorSNSeGuardaEnValueConImporteCero() {
        when(reglas.bloquearSiCorresponde(any(), anyBoolean())).thenReturn(Optional.empty());
        when(reglas.buscarNovedad(LEGAJO, ANHO, MES, 16)).thenReturn(Optional.empty());
        when(novedadCrossSlice.crear(eq(LEGAJO), eq(ANHO), eq(MES), eq(16), eq(BigDecimal.ZERO), eq("N"),
                eq("sin presentismo"))).thenReturn(novedad(1L));

        NovedadRegistradaIndividual registrada = useCase.registrar(LEGAJO, ANHO, MES,
                new RegistroNovedadIndividual(16, new BigDecimal("1500.50"), " n ", "sin presentismo", false));

        assertThat(registrada.resultado()).isEqualTo(EstadoProcesoIndividual.GUARDADA);
        verify(novedadCrossSlice).crear(LEGAJO, ANHO, MES, 16, BigDecimal.ZERO, "N", "sin presentismo");
    }

    @Test
    void importeNumericoSeGuardaComoImporte() {
        when(reglas.bloquearSiCorresponde(any(), anyBoolean())).thenReturn(Optional.empty());
        when(reglas.buscarNovedad(LEGAJO, ANHO, MES, 18)).thenReturn(Optional.empty());
        when(novedadCrossSlice.crear(eq(LEGAJO), eq(ANHO), eq(MES), eq(18), eq(new BigDecimal("1500.50")),
                eq(""), any())).thenReturn(novedad(2L));

        useCase.registrar(LEGAJO, ANHO, MES,
                new RegistroNovedadIndividual(18, new BigDecimal("1500.50"), "", null, false));

        verify(novedadCrossSlice).crear(LEGAJO, ANHO, MES, 18, new BigDecimal("1500.50"), "", null);
    }

    @Test
    void novedadExistenteSeActualizaEnLugarDeDuplicarse() {
        when(reglas.bloquearSiCorresponde(any(), anyBoolean())).thenReturn(Optional.empty());
        when(reglas.buscarNovedad(LEGAJO, ANHO, MES, 16)).thenReturn(Optional.of(novedad(77L)));
        when(novedadCrossSlice.actualizar(77L, new BigDecimal("900.00"), "", "obs")).thenReturn(novedad(77L));

        useCase.registrar(LEGAJO, ANHO, MES,
                new RegistroNovedadIndividual(16, new BigDecimal("900.00"), "", "obs", false));

        verify(novedadCrossSlice).actualizar(77L, new BigDecimal("900.00"), "", "obs");
        verify(novedadCrossSlice, never()).crear(any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void liquiacionBloqueadaNoEscribe() {
        when(reglas.bloquearSiCorresponde(any(), anyBoolean())).thenReturn(Optional.of(
                um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResultadoProcesoIndividual
                        .de(EstadoProcesoIndividual.BLOQUEADA, "bloqueada")));

        NovedadRegistradaIndividual registrada = useCase.registrar(LEGAJO, ANHO, MES,
                new RegistroNovedadIndividual(16, BigDecimal.ZERO, "N", null, false));

        assertThat(registrada.resultado()).isEqualTo(EstadoProcesoIndividual.BLOQUEADA);
        verify(novedadCrossSlice, never()).crear(any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void sinCodigoNoEscribe() {
        NovedadRegistradaIndividual registrada = useCase.registrar(LEGAJO, ANHO, MES,
                new RegistroNovedadIndividual(null, BigDecimal.ZERO, "", null, false));

        assertThat(registrada.resultado()).isEqualTo(EstadoProcesoIndividual.DATOS_INVALIDOS);
    }

    private NovedadView novedad(Long novedadId) {
        return new NovedadView(novedadId, 16, "PRESENTISMO", BigDecimal.ZERO, "N", null, null);
    }
}
