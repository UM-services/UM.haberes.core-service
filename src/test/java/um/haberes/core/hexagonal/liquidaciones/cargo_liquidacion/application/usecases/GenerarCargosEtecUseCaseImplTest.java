package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.exception.CargoLiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service.GeneracionCargosGuard;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service.GeneracionCargosOrquestador;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ResultadoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.UniversoCargos;

@ExtendWith(MockitoExtension.class)
class GenerarCargosEtecUseCaseImplTest {

    private static final Long LEGAJO = 2559L;

    @Mock
    private GeneracionCargosOrquestador orquestador;

    @Mock
    private GeneracionCargosGuard guard;

    @InjectMocks
    private GenerarCargosEtecUseCaseImpl useCase;

    private ResultadoGeneracionCargos resultado() {
        return new ResultadoGeneracionCargos(List.of(CargoLiquidacion.builder().legajoId(LEGAJO).build()),
                Map.of(UniversoCargos.ETEC, 1));
    }

    @Test
    void generarPorLegajoValidaElPeriodoYDelegaSoloEnEtec() {
        when(orquestador.generar(Set.of(UniversoCargos.ETEC), LEGAJO, 2026, 11)).thenReturn(resultado());

        var generado = useCase.generarByLegajo(LEGAJO, 2026, 11);

        verify(guard).validarLegajo(LEGAJO, 2026, 11);
        assertThat(generado.total()).isEqualTo(1);
    }

    @Test
    void generarPorPeriodoValidaYDelegaElPeriodoCompleto() {
        when(orquestador.generar(Set.of(UniversoCargos.ETEC), null, 2026, 11)).thenReturn(resultado());

        useCase.generarByPeriodo(2026, 11);

        verify(guard).validarPeriodo(2026, 11);
        verify(orquestador).generar(Set.of(UniversoCargos.ETEC), null, 2026, 11);
    }

    @Test
    void noGeneraSiElPeriodoYaEstaAcreditado() {
        org.mockito.Mockito.doThrow(new CargoLiquidacionException("acreditado"))
                .when(guard).validarLegajo(LEGAJO, 2026, 11);

        assertThatThrownBy(() -> useCase.generarByLegajo(LEGAJO, 2026, 11))
                .isInstanceOf(CargoLiquidacionException.class);
        verify(orquestador, never()).generar(Set.of(UniversoCargos.ETEC), LEGAJO, 2026, 11);
    }

    @Test
    void laPrevisualizacionNoValidaNiEscribe() {
        when(orquestador.previsualizar(Set.of(UniversoCargos.ETEC), LEGAJO, 2026, 11))
                .thenReturn(resultado());

        var cargos = useCase.previsualizarByLegajo(LEGAJO, 2026, 11);

        assertThat(cargos).hasSize(1);
        verify(guard, never()).validarLegajo(LEGAJO, 2026, 11);
        verify(orquestador, never()).generar(Set.of(UniversoCargos.ETEC), LEGAJO, 2026, 11);
    }
}
