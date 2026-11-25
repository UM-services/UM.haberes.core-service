package um.haberes.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.model.CodigoGrupoEntity;
import um.haberes.core.repository.JpaCodigoGrupoRepository;

@ExtendWith(MockitoExtension.class)
class CodigoGrupoServiceVersionTest {

    @Mock
    private JpaCodigoGrupoRepository repository;

    @InjectMocks
    private CodigoGrupoService service;

    @Test
    void versionCombinaCantidadUltimoUpdatedYUltimoId() {
        CodigoGrupoEntity entity = new CodigoGrupoEntity();
        entity.setCodigoId(989);
        entity.setUpdated(LocalDateTime.of(2026, 11, 24, 5, 51, 57));

        when(repository.count()).thenReturn(117L);
        when(repository.findTopByOrderByUpdatedDesc()).thenReturn(Optional.of(entity));
        when(repository.findTopByOrderByCodigoIdDesc()).thenReturn(Optional.of(entity));

        assertThat(service.version()).isEqualTo("117|2026-11-24T05:51:57|989");
    }

    @Test
    void versionFuncionaConCatalogoVacio() {
        when(repository.count()).thenReturn(0L);
        when(repository.findTopByOrderByUpdatedDesc()).thenReturn(Optional.empty());
        when(repository.findTopByOrderByCodigoIdDesc()).thenReturn(Optional.empty());

        assertThat(service.version()).isEqualTo("0||");
    }
}
