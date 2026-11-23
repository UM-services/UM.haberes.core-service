package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaPeriodoRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
class UpdateCategoriaUseCaseImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaPeriodoRepository categoriaPeriodoRepository;

    @InjectMocks
    private UpdateCategoriaUseCaseImpl useCase;

    @Test
    void actualizaLosPuntosDeUnaCategoriaExistente() {
        Categoria existente = Categoria.builder()
                .categoriaId(992)
                .nombre("DIRECTOR ETEC")
                .puntos(BigDecimal.ZERO)
                .docente((byte) 1)
                .build();
        when(categoriaRepository.findByCategoriaId(992)).thenReturn(Optional.of(existente));
        when(categoriaRepository.save(any(Categoria.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Categoria cambios = Categoria.builder()
                .categoriaId(992)
                .nombre("DIRECTOR ETEC")
                .basico(BigDecimal.ZERO)
                .puntos(new BigDecimal("1738.00"))
                .docente((byte) 1)
                .noDocente((byte) 0)
                .liquidaPorHora((byte) 0)
                .estadoDocente(BigDecimal.ZERO)
                .etecHoras((byte) 0)
                .build();

        Optional<Categoria> actualizada = useCase.updateCategoria(992, cambios, 2026, 11);

        assertTrue(actualizada.isPresent());
        assertEquals(0, new BigDecimal("1738.00").compareTo(actualizada.get().getPuntos()));
        verify(categoriaPeriodoRepository).upsert(existente, 2026, 11);
    }
}
