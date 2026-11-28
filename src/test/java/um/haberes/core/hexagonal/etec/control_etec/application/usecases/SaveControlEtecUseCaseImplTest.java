package um.haberes.core.hexagonal.etec.control_etec.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.etec.control_etec.application.exception.ControlEtecException;
import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.etec.control_etec.domain.ports.out.ControlEtecRepository;

@ExtendWith(MockitoExtension.class)
class SaveControlEtecUseCaseImplTest {

    @Mock
    private ControlEtecRepository repository;

    @InjectMocks
    private SaveControlEtecUseCaseImpl useCase;

    @Test
    void creaElPeriodoConLosValoresRecibidosYLosPorcentajesPorDefecto() {
        when(repository.findByPeriodo(2026, 11)).thenReturn(Optional.empty());
        when(repository.save(any(ControlEtec.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ControlEtec control = useCase.save(2026, 11, ControlEtec.builder()
                .valorPuntoEtec(new BigDecimal("311.3633"))
                .montoFijoCargo(new BigDecimal("50000.00"))
                .horasReferenciaCargo(new BigDecimal("18.00"))
                .basicoMaestroGrado(new BigDecimal("750000.00"))
                .build());

        assertThat(control.getControlEtecId()).isNull();
        assertThat(control.getAnho()).isEqualTo(2026);
        assertThat(control.getMes()).isEqualTo(11);
        assertThat(control.getValorPuntoEtec()).isEqualByComparingTo("311.3633");
        assertThat(control.getMontoFijoCargo()).isEqualByComparingTo("50000.00");
        assertThat(control.getHorasReferenciaCargo()).isEqualByComparingTo("18.00");
        assertThat(control.getBasicoMaestroGrado()).isEqualByComparingTo("750000.00");
        assertThat(control.getCodigoAdicionalDirectivo()).isNull();
        assertThat(control.getPorcentajePresentismo()).isEqualByComparingTo("20.00");
        assertThat(control.getPorcentajeAdicionalCarrera()).isEqualByComparingTo("10.00");
        assertThat(control.getGarantia()).isEqualByComparingTo("0.00");
    }

    @Test
    void actualizaElPeriodoExistenteConservandoElIdYLosValoresNoInformados() {
        ControlEtec existente = ControlEtec.builder()
                .controlEtecId(7L)
                .anho(2026)
                .mes(11)
                .valorPuntoEtec(new BigDecimal("300.0000"))
                .montoFijoCargo(new BigDecimal("10000.00"))
                .horasReferenciaCargo(new BigDecimal("18.00"))
                .basicoMaestroGrado(new BigDecimal("700000.00"))
                .codigoAdicionalDirectivo(990)
                .porcentajePresentismo(new BigDecimal("25.00"))
                .porcentajeAdicionalCarrera(new BigDecimal("12.00"))
                .garantia(new BigDecimal("844872.78"))
                .horasAdicionalCargaHoraria(new BigDecimal("15.00"))
                .valorHoraAdicionalCargaHoraria(new BigDecimal("8012.19"))
                .build();
        when(repository.findByPeriodo(2026, 11)).thenReturn(Optional.of(existente));
        when(repository.save(any(ControlEtec.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ControlEtec control = useCase.save(2026, 11, ControlEtec.builder()
                .valorPuntoEtec(new BigDecimal("311.3633"))
                .build());

        assertThat(control.getControlEtecId()).isEqualTo(7L);
        assertThat(control.getValorPuntoEtec()).isEqualByComparingTo("311.3633");
        assertThat(control.getMontoFijoCargo()).isEqualByComparingTo("10000.00");
        assertThat(control.getHorasReferenciaCargo()).isEqualByComparingTo("18.00");
        assertThat(control.getBasicoMaestroGrado()).isEqualByComparingTo("700000.00");
        assertThat(control.getCodigoAdicionalDirectivo()).isEqualTo(990);
        assertThat(control.getPorcentajePresentismo()).isEqualByComparingTo("25.00");
        assertThat(control.getGarantia()).isEqualByComparingTo("844872.78");
        assertThat(control.getHorasAdicionalCargaHoraria()).isEqualByComparingTo("15.00");
    }

    @Test
    void rechazaValoresNegativosSinGuardar() {
        when(repository.findByPeriodo(2026, 11)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.save(2026, 11, ControlEtec.builder()
                .valorPuntoEtec(new BigDecimal("-1.00"))
                .build()))
                .isInstanceOf(ControlEtecException.class)
                .hasMessageContaining("valor del punto");

        verify(repository, never()).save(any());
    }

    @Test
    void rechazaUnMesFueraDeRango() {
        assertThatThrownBy(() -> useCase.save(2026, 13, ControlEtec.builder().build()))
                .isInstanceOf(ControlEtecException.class)
                .hasMessageContaining("mes");
    }
}
