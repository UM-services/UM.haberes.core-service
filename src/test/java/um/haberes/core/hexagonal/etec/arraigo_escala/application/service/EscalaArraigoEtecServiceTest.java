package um.haberes.core.hexagonal.etec.arraigo_escala.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.etec.arraigo_escala.domain.model.EscalaArraigoEtec;
import um.haberes.core.hexagonal.etec.arraigo_escala.domain.ports.in.GetEscalaArraigoEtecUseCase;
import um.haberes.core.hexagonal.etec.arraigo_escala.domain.ports.in.ReplaceEscalaArraigoEtecUseCase;

@ExtendWith(MockitoExtension.class)
class EscalaArraigoEtecServiceTest {

    @Mock
    private GetEscalaArraigoEtecUseCase getEscalaArraigoEtecUseCase;

    @Mock
    private ReplaceEscalaArraigoEtecUseCase replaceEscalaArraigoEtecUseCase;

    @InjectMocks
    private EscalaArraigoEtecService service;

    private EscalaArraigoEtec tramo(int anios, String porcentaje) {
        return new EscalaArraigoEtec((long) anios, anios, new BigDecimal(porcentaje));
    }

    @Test
    void usaElTramoDeMayorAntiguedadQueNoSupereLosAnios() {
        when(getEscalaArraigoEtecUseCase.findAll()).thenReturn(List.of(
                tramo(2, "20.00"), tramo(5, "35.00"), tramo(10, "60.00")));

        assertThat(service.porcentajeParaAnios(1)).isEqualByComparingTo("0");
        assertThat(service.porcentajeParaAnios(2)).isEqualByComparingTo("20.00");
        assertThat(service.porcentajeParaAnios(4)).isEqualByComparingTo("20.00");
        assertThat(service.porcentajeParaAnios(5)).isEqualByComparingTo("35.00");
        assertThat(service.porcentajeParaAnios(9)).isEqualByComparingTo("35.00");
        assertThat(service.porcentajeParaAnios(10)).isEqualByComparingTo("60.00");
        assertThat(service.porcentajeParaAnios(16)).isEqualByComparingTo("60.00");
    }

    @Test
    void sinEscalaConfiguradaDevuelveCero() {
        when(getEscalaArraigoEtecUseCase.findAll()).thenReturn(List.of());

        assertThat(service.porcentajeParaAnios(20)).isEqualByComparingTo("0");
    }
}
