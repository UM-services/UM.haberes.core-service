package um.haberes.core.hexagonal.liquidaciones.individual.application.usecases;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.CargoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.model.CargoLiquidacionView;
import um.haberes.core.hexagonal.crossslice.liquidacion.LiquidacionCrossSlice;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.liquidacion_proceso.ProcesoLiquidacionCrossSlice;
import um.haberes.core.hexagonal.liquidaciones.individual.application.service.LiquidacionIndividualReglas;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.EstadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResumenGeneracionCargos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerarCargosIndividualUseCaseImplTest {

    private static final Long LEGAJO = 1001L;

    private static final Integer ANHO = 2026;

    private static final Integer MES = 11;

    @Mock
    private LiquidacionCrossSlice liquidacionCrossSlice;

    @Mock
    private CargoLiquidacionCrossSlice cargoLiquidacionCrossSlice;

    @Mock
    private ProcesoLiquidacionCrossSlice procesoLiquidacionCrossSlice;

    @Mock
    private LiquidacionIndividualReglas reglas;

    @InjectMocks
    private GenerarCargosIndividualUseCaseImpl useCase;

    @Test
    void bloqueadaNoGeneraCargos() {
        when(reglas.bloquearSiCorresponde(any(), anyBoolean())).thenReturn(Optional.of(
                um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResultadoProcesoIndividual
                        .de(EstadoProcesoIndividual.BLOQUEADA, "bloqueada")));

        ResumenGeneracionCargos resumen = useCase.generar(LEGAJO, ANHO, MES);

        assertThat(resumen.resultado()).isEqualTo(EstadoProcesoIndividual.BLOQUEADA);
        assertThat(resumen.docentes()).isZero();
        verify(procesoLiquidacionCrossSlice, never()).generarCargos(anyLong(), anyInt(), anyInt());
    }

    @Test
    void sinControlNoGeneraCargos() {
        when(reglas.bloquearSiCorresponde(any(), anyBoolean())).thenReturn(Optional.empty());
        when(reglas.hayControl(ANHO, MES)).thenReturn(false);

        ResumenGeneracionCargos resumen = useCase.generar(LEGAJO, ANHO, MES);

        assertThat(resumen.resultado()).isEqualTo(EstadoProcesoIndividual.SIN_CONTROL);
        verify(procesoLiquidacionCrossSlice, never()).generarCargos(anyLong(), anyInt(), anyInt());
    }

    @Test
    void generacionDevuelveConteosPorTipo() {
        when(reglas.bloquearSiCorresponde(any(), anyBoolean())).thenReturn(Optional.empty());
        when(reglas.hayControl(ANHO, MES)).thenReturn(true);
        when(cargoLiquidacionCrossSlice.porLegajo(LEGAJO, ANHO, MES))
                .thenReturn(List.of(cargo(true), cargo(true), cargo(false)));

        ResumenGeneracionCargos resumen = useCase.generar(LEGAJO, ANHO, MES);

        assertThat(resumen.resultado()).isEqualTo(EstadoProcesoIndividual.GENERADOS);
        assertThat(resumen.docentes()).isEqualTo(2);
        assertThat(resumen.noDocentes()).isEqualTo(1);
        verify(procesoLiquidacionCrossSlice).generarCargos(LEGAJO, ANHO, MES);
    }

    private CargoLiquidacionView cargo(boolean docente) {
        return new CargoLiquidacionView(1L, 7, "FING", OffsetDateTime.now(), OffsetDateTime.now(), 1,
                "PROFESOR", BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.TEN, 1, 0, "A", docente);
    }

    @SuppressWarnings("unused")
    private LiquidacionView liquidacion() {
        return new LiquidacionView(1L, ANHO, MES, (byte) 0, 1, "S", BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO, null, null, 7, "B");
    }
}
