package um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.entity.CodigoEntity;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.mapper.CodigoMapper;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.repository.JpaCodigoRepository;

@ExtendWith(MockitoExtension.class)
class JpaCodigoRepositoryAdapterVersionTest {

    @Mock
    private JpaCodigoRepository jpaCodigoRepository;

    @Mock
    private CodigoMapper codigoMapper;

    @InjectMocks
    private JpaCodigoRepositoryAdapter adapter;

    @Test
    void versionCombinaCantidadUltimoUpdatedYUltimoId() {
        CodigoEntity entity = new CodigoEntity();
        entity.setCodigoId(989);
        entity.setUpdated(LocalDateTime.of(2026, 11, 24, 5, 51, 57));

        when(jpaCodigoRepository.count()).thenReturn(116L);
        when(jpaCodigoRepository.findTopByOrderByUpdatedDesc()).thenReturn(Optional.of(entity));
        when(jpaCodigoRepository.findTopByOrderByCodigoId()).thenReturn(Optional.of(entity));

        assertThat(adapter.version()).isEqualTo("116|2026-11-24T05:51:57|989");
    }

    @Test
    void versionFuncionaConCatalogoVacio() {
        when(jpaCodigoRepository.count()).thenReturn(0L);
        when(jpaCodigoRepository.findTopByOrderByUpdatedDesc()).thenReturn(Optional.empty());
        when(jpaCodigoRepository.findTopByOrderByCodigoId()).thenReturn(Optional.empty());

        assertThat(adapter.version()).isEqualTo("0||");
    }
}
