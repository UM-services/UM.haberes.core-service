package um.haberes.core.hexagonal.etec.arraigo_escala.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.etec.arraigo_escala.application.exception.EscalaArraigoEtecException;
import um.haberes.core.hexagonal.etec.arraigo_escala.domain.model.EscalaArraigoEtec;
import um.haberes.core.hexagonal.etec.arraigo_escala.domain.ports.out.EscalaArraigoEtecRepository;

@ExtendWith(MockitoExtension.class)
class ReplaceEscalaArraigoEtecUseCaseImplTest {

    @Mock
    private EscalaArraigoEtecRepository repository;

    @InjectMocks
    private ReplaceEscalaArraigoEtecUseCaseImpl useCase;

    private EscalaArraigoEtec tramo(Integer anios, String porcentaje) {
        return new EscalaArraigoEtec(null, anios, porcentaje == null ? null : new BigDecimal(porcentaje));
    }

    @Test
    void reemplazaLaEscalaOrdenadaYConDosDecimales() {
        when(repository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        List<EscalaArraigoEtec> resultado = useCase.reemplazar(List.of(
                tramo(10, "60"), tramo(2, "20.5"), tramo(5, "35")));

        verify(repository).deleteAll();
        assertThat(resultado).extracting(EscalaArraigoEtec::getAniosMinimos).containsExactly(2, 5, 10);
        assertThat(resultado.get(0).getPorcentaje()).isEqualByComparingTo("20.50");
        assertThat(resultado.get(1).getPorcentaje()).isEqualByComparingTo("35.00");
        assertThat(resultado.get(2).getPorcentaje()).isEqualByComparingTo("60.00");
    }

    @Test
    void rechazaLaListaVaciaSinBorrarNada() {
        assertThatThrownBy(() -> useCase.reemplazar(List.of()))
                .isInstanceOf(EscalaArraigoEtecException.class)
                .hasMessageContaining("al menos un tramo");

        verify(repository, never()).deleteAll();
    }

    @Test
    void rechazaLaAntiguedadRepetida() {
        assertThatThrownBy(() -> useCase.reemplazar(List.of(tramo(2, "20"), tramo(2, "25"))))
                .isInstanceOf(EscalaArraigoEtecException.class)
                .hasMessageContaining("repetida");

        verify(repository, never()).deleteAll();
    }

    @Test
    void rechazaLaAntiguedadInvalida() {
        assertThatThrownBy(() -> useCase.reemplazar(List.of(tramo(0, "20"))))
                .isInstanceOf(EscalaArraigoEtecException.class)
                .hasMessageContaining("al menos 1 año");

        assertThatThrownBy(() -> useCase.reemplazar(List.of(tramo(null, "20"))))
                .isInstanceOf(EscalaArraigoEtecException.class)
                .hasMessageContaining("al menos 1 año");
    }

    @Test
    void rechazaElPorcentajeFueraDeRango() {
        assertThatThrownBy(() -> useCase.reemplazar(List.of(tramo(2, "-1"))))
                .isInstanceOf(EscalaArraigoEtecException.class)
                .hasMessageContaining("entre 0 y 100");

        assertThatThrownBy(() -> useCase.reemplazar(List.of(tramo(2, "101"))))
                .isInstanceOf(EscalaArraigoEtecException.class)
                .hasMessageContaining("entre 0 y 100");
    }

    @Test
    void rechazaElTramoSinPorcentaje() {
        assertThatThrownBy(() -> useCase.reemplazar(List.of(tramo(2, null))))
                .isInstanceOf(EscalaArraigoEtecException.class)
                .hasMessageContaining("no tiene porcentaje");
    }

    @Test
    void guardaLaEscalaNormalizada() {
        when(repository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        useCase.reemplazar(List.of(tramo(2, "20.00")));

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<EscalaArraigoEtec>> captor = ArgumentCaptor.forClass(List.class);
        verify(repository).saveAll(captor.capture());
        assertThat(captor.getValue()).hasSize(1);
        assertThat(captor.getValue().getFirst().getAniosMinimos()).isEqualTo(2);
    }
}
