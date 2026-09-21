package um.haberes.core.hexagonal.liquidaciones.bono.application.usecases;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.Actividad;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.FaltanteBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.IntegridadBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.PeriodoBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.ActividadRepository;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.ItemQueryPort;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.LegajoControlQueryPort;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.LiquidacionQueryPort;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.PersonaQueryPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerificarIntegridadBonoUseCaseImplTest {

    @Mock
    private PersonaQueryPort personaQueryPort;

    @Mock
    private LiquidacionQueryPort liquidacionQueryPort;

    @Mock
    private ItemQueryPort itemQueryPort;

    @Mock
    private ActividadRepository actividadRepository;

    @Mock
    private LegajoControlQueryPort legajoControlQueryPort;

    @InjectMocks
    private VerificarIntegridadBonoUseCaseImpl useCase;

    private PeriodoBono periodo() {
        return PeriodoBono.builder().legajoId(12345L).anho(2026).mes(8).build();
    }

    @Test
    void verificarIntegridadTodoOk() {
        when(personaQueryPort.findDependenciaIdByLegajoId(12345L)).thenReturn(Optional.of(7));
        when(liquidacionQueryPort.existsByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(true);
        when(itemQueryPort.existsByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(true);
        when(actividadRepository.findByLegajoIdAndAnhoAndMes(12345L, 2026, 8))
                .thenReturn(Optional.of(Actividad.builder().actividadId(1L).build()));
        when(legajoControlQueryPort.existsByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(true);

        IntegridadBono result = useCase.verificarIntegridad(periodo());

        assertThat(result.isOk()).isTrue();
        assertThat(result.getFaltantes()).isEmpty();
        assertThat(result.getLegajoId()).isEqualTo(12345L);
        assertThat(result.getAnho()).isEqualTo(2026);
        assertThat(result.getMes()).isEqualTo(8);
    }

    @Test
    void verificarIntegridadSinDependencia() {
        when(personaQueryPort.findDependenciaIdByLegajoId(12345L)).thenReturn(Optional.empty());
        when(liquidacionQueryPort.existsByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(true);
        when(itemQueryPort.existsByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(true);
        when(actividadRepository.findByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(Optional.empty());
        when(legajoControlQueryPort.existsByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(true);

        IntegridadBono result = useCase.verificarIntegridad(periodo());

        assertThat(result.isOk()).isFalse();
        assertThat(result.getFaltantes()).containsExactly(FaltanteBono.DEPENDENCIA, FaltanteBono.ACTIVIDAD);
    }

    @Test
    void verificarIntegridadFaltantesEnOrden() {
        when(personaQueryPort.findDependenciaIdByLegajoId(12345L)).thenReturn(Optional.empty());
        when(liquidacionQueryPort.existsByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(false);
        when(itemQueryPort.existsByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(false);
        when(actividadRepository.findByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(Optional.empty());
        when(legajoControlQueryPort.existsByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(false);

        IntegridadBono result = useCase.verificarIntegridad(periodo());

        assertThat(result.getFaltantes()).containsExactly(
                FaltanteBono.DEPENDENCIA,
                FaltanteBono.LIQUIDACION,
                FaltanteBono.ITEM,
                FaltanteBono.ACTIVIDAD,
                FaltanteBono.LEGAJO_CONTROL);
    }
}
