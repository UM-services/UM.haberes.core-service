package um.haberes.core.hexagonal.etec.control_etec.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.etec.control_etec.application.exception.ControlEtecOrigenException;
import um.haberes.core.hexagonal.etec.control_etec.application.exception.ControlEtecYaExisteException;
import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.etec.control_etec.domain.ports.out.ControlEtecRepository;
import um.haberes.core.hexagonal.etec.hora_escala.domain.model.EscalaHoraEtec;
import um.haberes.core.hexagonal.etec.hora_escala.domain.ports.out.EscalaHoraEtecRepository;

@ExtendWith(MockitoExtension.class)
class CopiarControlEtecUseCaseImplTest {

    @Mock
    private ControlEtecRepository repository;

    @Mock
    private EscalaHoraEtecRepository escalaHoraEtecRepository;

    @InjectMocks
    private CopiarControlEtecUseCaseImpl useCase;

    private ControlEtec origen(Integer anho, Integer mes) {
        return ControlEtec.builder()
                .controlEtecId(5L)
                .anho(anho)
                .mes(mes)
                .valorPuntoEtec(new BigDecimal("311.3633"))
                .montoFijoCargo(new BigDecimal("50000.00"))
                .horasReferenciaCargo(new BigDecimal("18.00"))
                .basicoMaestroGrado(new BigDecimal("750000.00"))
                .porcentajePresentismo(new BigDecimal("20.00"))
                .porcentajeAdicionalCarrera(new BigDecimal("10.00"))
                .garantia(new BigDecimal("844872.78"))
                .horasAdicionalCargaHoraria(new BigDecimal("15.00"))
                .valorHoraAdicionalCargaHoraria(new BigDecimal("8012.19"))
                .build();
    }

    @Test
    void copiaTodosLosParametrosDelMesAnterior() {
        when(repository.findByPeriodo(2026, 10)).thenReturn(Optional.of(origen(2026, 10)));
        when(repository.save(any(ControlEtec.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ControlEtec copia = useCase.copiar(2026, 11);

        assertThat(copia.getControlEtecId()).isNull();
        assertThat(copia.getAnho()).isEqualTo(2026);
        assertThat(copia.getMes()).isEqualTo(11);
        assertThat(copia.getValorPuntoEtec()).isEqualByComparingTo("311.3633");
        assertThat(copia.getMontoFijoCargo()).isEqualByComparingTo("50000.00");
        assertThat(copia.getHorasReferenciaCargo()).isEqualByComparingTo("18.00");
        assertThat(copia.getBasicoMaestroGrado()).isEqualByComparingTo("750000.00");
        assertThat(copia.getGarantia()).isEqualByComparingTo("844872.78");
        assertThat(copia.getHorasAdicionalCargaHoraria()).isEqualByComparingTo("15.00");
        assertThat(copia.getValorHoraAdicionalCargaHoraria()).isEqualByComparingTo("8012.19");
    }

    @Test
    void copiaEneroDesdeDiciembreDelAnioAnterior() {
        when(repository.findByPeriodo(2025, 12)).thenReturn(Optional.of(origen(2025, 12)));
        when(repository.save(any(ControlEtec.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ControlEtec copia = useCase.copiar(2026, 1);

        assertThat(copia.getAnho()).isEqualTo(2026);
        assertThat(copia.getMes()).isEqualTo(1);
        assertThat(copia.getValorPuntoEtec()).isEqualByComparingTo("311.3633");
    }

    @Test
    void copiaLaEscalaDeHorasApuntandoAlControlNuevo() {
        when(repository.findByPeriodo(2026, 10)).thenReturn(Optional.of(origen(2026, 10)));
        when(repository.save(any(ControlEtec.class))).thenAnswer(invocation -> {
            ControlEtec control = invocation.getArgument(0);
            control.setControlEtecId(51L);
            return control;
        });
        when(escalaHoraEtecRepository.findAllByControlEtecId(5L)).thenReturn(List.of(
                EscalaHoraEtec.builder()
                        .escalaHoraEtecId(9L)
                        .controlEtecId(5L)
                        .categoriaId(2002)
                        .horasDesde(new BigDecimal("0.00"))
                        .horasHasta(new BigDecimal("12.00"))
                        .montoFijo(new BigDecimal("50000.00"))
                        .valorHora(new BigDecimal("5000.00"))
                        .build()));

        useCase.copiar(2026, 11);

        @SuppressWarnings("unchecked")
        org.mockito.ArgumentCaptor<List<EscalaHoraEtec>> captor =
                org.mockito.ArgumentCaptor.forClass(List.class);
        verify(escalaHoraEtecRepository).saveAll(captor.capture());
        assertThat(captor.getValue()).singleElement().satisfies(tramo -> {
            assertThat(tramo.getEscalaHoraEtecId()).isNull();
            assertThat(tramo.getControlEtecId()).isEqualTo(51L);
            assertThat(tramo.getCategoriaId()).isEqualTo(2002);
            assertThat(tramo.getHorasDesde()).isEqualByComparingTo("0.00");
            assertThat(tramo.getValorHora()).isEqualByComparingTo("5000.00");
        });
    }

    @Test
    void sinTramosNoLlamaAlGuardadoDeLaEscala() {
        when(repository.findByPeriodo(2026, 10)).thenReturn(Optional.of(origen(2026, 10)));
        when(repository.save(any(ControlEtec.class))).thenAnswer(invocation -> {
            ControlEtec control = invocation.getArgument(0);
            control.setControlEtecId(51L);
            return control;
        });
        when(escalaHoraEtecRepository.findAllByControlEtecId(5L)).thenReturn(List.of());

        useCase.copiar(2026, 11);

        verify(escalaHoraEtecRepository, never()).saveAll(any());
    }

    @Test
    void rechazaSiElPeriodoYaTieneControlEtec() {
        when(repository.existsByPeriodo(2026, 11)).thenReturn(true);

        assertThatThrownBy(() -> useCase.copiar(2026, 11))
                .isInstanceOf(ControlEtecYaExisteException.class);

        verify(repository, never()).save(any());
    }

    @Test
    void rechazaSiNoExisteElPeriodoAnterior() {
        when(repository.existsByPeriodo(2026, 11)).thenReturn(false);
        when(repository.findByPeriodo(2026, 10)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.copiar(2026, 11))
                .isInstanceOf(ControlEtecOrigenException.class);
    }
}
