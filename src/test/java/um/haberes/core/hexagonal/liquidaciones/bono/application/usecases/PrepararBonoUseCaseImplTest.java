package um.haberes.core.hexagonal.liquidaciones.bono.application.usecases;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.bono.application.exception.BonoException;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.Actividad;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.PeriodoBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.ActividadRepository;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.CargoClaseDetalleQueryPort;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.CargoLiquidacionQueryPort;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.LiquidacionQueryPort;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.PersonaQueryPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrepararBonoUseCaseImplTest {

    @Mock
    private LiquidacionQueryPort liquidacionQueryPort;

    @Mock
    private ActividadRepository actividadRepository;

    @Mock
    private CargoLiquidacionQueryPort cargoLiquidacionQueryPort;

    @Mock
    private CargoClaseDetalleQueryPort cargoClaseDetalleQueryPort;

    @Mock
    private PersonaQueryPort personaQueryPort;

    @InjectMocks
    private PrepararBonoUseCaseImpl useCase;

    private PeriodoBono periodo() {
        return PeriodoBono.builder().legajoId(12345L).anho(2026).mes(8).build();
    }

    @Test
    void prepararSinLiquidacionLanzaExcepcion() {
        when(liquidacionQueryPort.existsByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(false);

        assertThatThrownBy(() -> useCase.preparar(periodo()))
                .isInstanceOf(BonoException.class)
                .hasMessageContaining("LIQUIDACION");
        verify(actividadRepository, never()).save(any());
    }

    @Test
    void prepararRecalculaFlagsDocenteOtrasClases() {
        when(liquidacionQueryPort.existsByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(true);
        Actividad existente = Actividad.builder()
                .actividadId(5L)
                .legajoId(12345L)
                .anho(2026)
                .mes(8)
                .docente((byte) 0)
                .otras((byte) 1)
                .clases((byte) 1)
                .build();
        when(actividadRepository.findByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(Optional.of(existente));
        when(cargoLiquidacionQueryPort.findBasicoCargosDocentesByLegajoAndPeriodo(12345L, 2026, 8))
                .thenReturn(List.of(BigDecimal.ZERO, new BigDecimal("120.50")));
        when(cargoLiquidacionQueryPort.findBasicoCargosNoDocentesByLegajoAndPeriodo(12345L, 2026, 8))
                .thenReturn(List.of());
        when(cargoClaseDetalleQueryPort.existsByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(false);
        when(actividadRepository.save(any(Actividad.class))).thenAnswer(inv -> inv.getArgument(0));

        Actividad result = useCase.preparar(periodo());

        assertThat(result.getDocente()).isEqualTo((byte) 1);
        assertThat(result.getOtras()).isEqualTo((byte) 0);
        assertThat(result.getClases()).isEqualTo((byte) 0);
    }

    @Test
    void prepararConBasicoCeroNoActivaFlag() {
        when(liquidacionQueryPort.existsByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(true);
        when(actividadRepository.findByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(Optional.empty());
        when(personaQueryPort.findDependenciaIdByLegajoId(12345L)).thenReturn(Optional.of(7));
        when(cargoLiquidacionQueryPort.findBasicoCargosDocentesByLegajoAndPeriodo(12345L, 2026, 8))
                .thenReturn(java.util.Arrays.asList(BigDecimal.ZERO, null));
        when(cargoLiquidacionQueryPort.findBasicoCargosNoDocentesByLegajoAndPeriodo(12345L, 2026, 8))
                .thenReturn(List.of(BigDecimal.ZERO));
        when(cargoClaseDetalleQueryPort.existsByLegajoIdAndAnhoAndMes(12345L, 2026, 8)).thenReturn(true);
        when(actividadRepository.save(any(Actividad.class))).thenAnswer(inv -> inv.getArgument(0));

        Actividad result = useCase.preparar(periodo());

        assertThat(result.getActividadId()).isNull();
        assertThat(result.getLegajoId()).isEqualTo(12345L);
        assertThat(result.getDependenciaId()).isEqualTo(7);
        assertThat(result.getDocente()).isEqualTo((byte) 0);
        assertThat(result.getOtras()).isEqualTo((byte) 0);
        assertThat(result.getClases()).isEqualTo((byte) 1);

        ArgumentCaptor<Actividad> captor = ArgumentCaptor.forClass(Actividad.class);
        verify(actividadRepository).save(captor.capture());
        assertThat(captor.getValue().getAnho()).isEqualTo(2026);
    }
}
