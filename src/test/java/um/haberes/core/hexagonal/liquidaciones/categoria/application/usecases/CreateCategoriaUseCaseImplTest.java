package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.categoria.application.exception.CategoriaException;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaPeriodoRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
class CreateCategoriaUseCaseImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaPeriodoRepository categoriaPeriodoRepository;

    @InjectMocks
    private CreateCategoriaUseCaseImpl useCase;

    @Test
    void rechazaUnIdExistente() {
        when(categoriaRepository.findByCategoriaId(10))
                .thenReturn(Optional.of(Categoria.builder().categoriaId(10).build()));

        assertThrows(CategoriaException.class, () -> useCase.createCategoria(
                Categoria.builder()
                        .categoriaId(10)
                        .nombre("Directora ETEC")
                        .puntos(new BigDecimal("1738.00"))
                        .build(),
                2026, 11));

        verify(categoriaRepository, never()).save(any());
        verifyNoInteractions(categoriaPeriodoRepository);
    }

    @Test
    void creaUnaCategoriaNuevaYVersionaElPeriodo() {
        Categoria nueva = Categoria.builder()
                .categoriaId(1307)
                .nombre("Directora ETEC")
                .puntos(new BigDecimal("1738.00"))
                .docente((byte) 1)
                .build();
        when(categoriaRepository.findByCategoriaId(1307)).thenReturn(Optional.empty());
        when(categoriaRepository.save(nueva)).thenReturn(nueva);

        Categoria creada = useCase.createCategoria(nueva, 2026, 11);

        assertSame(nueva, creada);
        verify(categoriaRepository).save(nueva);
        verify(categoriaPeriodoRepository).upsert(nueva, 2026, 11);
    }
}
