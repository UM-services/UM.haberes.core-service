package um.haberes.core.hexagonal.etec.presentismo.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.etec.presentismo.domain.model.EtecPresentismo;
import um.haberes.core.hexagonal.etec.presentismo.domain.ports.out.EtecPresentismoRepository;

@ExtendWith(MockitoExtension.class)
class GetEtecPresentismoUseCaseImplTest {

    @Mock
    private EtecPresentismoRepository repository;

    @InjectMocks
    private GetEtecPresentismoUseCaseImpl useCase;

    @Test
    void devuelveLasCategoriasDistintasQuePierdenPresentismo() {
        when(repository.findAllByLegajoIdAndPeriodo(100L, 2026, 11)).thenReturn(List.of(
                EtecPresentismo.builder().categoriaId(992).build(),
                EtecPresentismo.builder().categoriaId(992).build(),
                EtecPresentismo.builder().categoriaId(993).build(),
                EtecPresentismo.builder().categoriaId(null).build()));

        assertThat(useCase.getCategoriasSinPresentismo(100L, 2026, 11))
                .containsExactlyInAnyOrder(992, 993);
    }
}
