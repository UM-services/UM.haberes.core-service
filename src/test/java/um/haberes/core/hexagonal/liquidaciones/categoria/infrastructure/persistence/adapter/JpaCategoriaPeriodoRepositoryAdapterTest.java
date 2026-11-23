package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.repository.JpaCategoriaRepository;
import um.haberes.core.model.CategoriaPeriodoEntity;
import um.haberes.core.repository.JpaCategoriaPeriodoRepository;
import um.haberes.core.repository.view.JpaCategoriaByPeriodoRepository;

@ExtendWith(MockitoExtension.class)
class JpaCategoriaPeriodoRepositoryAdapterTest {

    @Mock
    private JpaCategoriaPeriodoRepository jpaCategoriaPeriodoRepository;

    @Mock
    private JpaCategoriaByPeriodoRepository jpaCategoriaByPeriodoRepository;

    @Mock
    private JpaCategoriaRepository jpaCategoriaRepository;

    @InjectMocks
    private JpaCategoriaPeriodoRepositoryAdapter adapter;

    @Test
    void upsertCreaLaFilaConPuntosYFlagDeHoras() {
        when(jpaCategoriaPeriodoRepository.findByCategoriaIdAndAnhoAndMes(2002, 2026, 11))
                .thenReturn(Optional.empty());

        adapter.upsert(categoria(2002, "1738.00", (byte) 1), 2026, 11);

        ArgumentCaptor<CategoriaPeriodoEntity> captor = ArgumentCaptor.forClass(CategoriaPeriodoEntity.class);
        verify(jpaCategoriaPeriodoRepository).save(captor.capture());
        assertEquals(0, new BigDecimal("1738.00").compareTo(captor.getValue().getPuntos()));
        assertEquals((byte) 1, captor.getValue().getEtecHoras());
    }

    @Test
    void upsertActualizaPuntosYFlagDeLaFilaExistente() {
        CategoriaPeriodoEntity existente = new CategoriaPeriodoEntity();
        existente.setCategoriaId(2002);
        existente.setAnho(2026);
        existente.setMes(11);
        when(jpaCategoriaPeriodoRepository.findByCategoriaIdAndAnhoAndMes(2002, 2026, 11))
                .thenReturn(Optional.of(existente));

        adapter.upsert(categoria(2002, "1469.00", (byte) 0), 2026, 11);

        verify(jpaCategoriaPeriodoRepository).save(existente);
        assertEquals(0, new BigDecimal("1469.00").compareTo(existente.getPuntos()));
        assertEquals((byte) 0, existente.getEtecHoras());
    }

    @Test
    @SuppressWarnings("unchecked")
    void upsertAllCopiaPuntosYFlagDeHoras() {
        when(jpaCategoriaPeriodoRepository.findAllByAnhoAndMes(2026, 11)).thenReturn(List.of());

        adapter.upsertAll(List.of(categoria(2002, "1000.00", (byte) 1)), 2026, 11);

        ArgumentCaptor<List<CategoriaPeriodoEntity>> captor = ArgumentCaptor.forClass(List.class);
        verify(jpaCategoriaPeriodoRepository).saveAll(captor.capture());
        CategoriaPeriodoEntity fila = captor.getValue().getFirst();
        assertEquals(0, new BigDecimal("1000.00").compareTo(fila.getPuntos()));
        assertEquals((byte) 1, fila.getEtecHoras());
    }

    private Categoria categoria(Integer categoriaId, String puntos, byte etecHoras) {
        return Categoria.builder()
                .categoriaId(categoriaId)
                .nombre("DIRECTOR ETEC")
                .basico(BigDecimal.ZERO)
                .docente((byte) 1)
                .noDocente((byte) 0)
                .liquidaPorHora((byte) 0)
                .estadoDocente(BigDecimal.ZERO)
                .puntos(new BigDecimal(puntos))
                .etecHoras(etecHoras)
                .build();
    }
}
