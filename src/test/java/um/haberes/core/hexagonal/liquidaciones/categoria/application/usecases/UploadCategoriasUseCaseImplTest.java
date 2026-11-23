package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.UploadedFile;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaPeriodoRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaUploadFileReader;

@ExtendWith(MockitoExtension.class)
class UploadCategoriasUseCaseImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaPeriodoRepository categoriaPeriodoRepository;

    @Mock
    private CategoriaUploadFileReader categoriaUploadFileReader;

    @InjectMocks
    private UploadCategoriasUseCaseImpl useCase;

    @Test
    @SuppressWarnings("unchecked")
    void conservaLosPuntosCuandoLaPlanillaNoTraeLaColumna() {
        Categoria existente = Categoria.builder()
                .categoriaId(992)
                .nombre("DIRECTOR ETEC")
                .puntos(new BigDecimal("1738.00"))
                .build();
        when(categoriaRepository.findAll()).thenReturn(List.of(existente));
        Categoria fila = Categoria.builder()
                .categoriaId(992)
                .nombre("DIRECTOR ETEC")
                .puntos(null)
                .build();
        when(categoriaUploadFileReader.readCategorias(any(UploadedFile.class))).thenReturn(List.of(fila));

        useCase.uploadCategorias(UploadedFile.builder().filename("categorias.xlsx").base64("").build(), 2026, 11);

        ArgumentCaptor<List<Categoria>> captor = ArgumentCaptor.forClass(List.class);
        verify(categoriaRepository).saveAll(captor.capture());
        assertEquals(0, new BigDecimal("1738.00").compareTo(captor.getValue().get(0).getPuntos()));
    }

    @Test
    @SuppressWarnings("unchecked")
    void aplicaLosPuntosDeLaPlanillaCuandoLaColumnaEstaPresente() {
        Categoria existente = Categoria.builder()
                .categoriaId(992)
                .nombre("DIRECTOR ETEC")
                .puntos(new BigDecimal("1738.00"))
                .build();
        when(categoriaRepository.findAll()).thenReturn(List.of(existente));
        Categoria fila = Categoria.builder()
                .categoriaId(992)
                .nombre("DIRECTOR ETEC")
                .puntos(new BigDecimal("1500.00"))
                .build();
        when(categoriaUploadFileReader.readCategorias(any(UploadedFile.class))).thenReturn(List.of(fila));

        useCase.uploadCategorias(UploadedFile.builder().filename("categorias.xlsx").base64("").build(), 2026, 11);

        ArgumentCaptor<List<Categoria>> captor = ArgumentCaptor.forClass(List.class);
        verify(categoriaRepository).saveAll(captor.capture());
        assertEquals(0, new BigDecimal("1500.00").compareTo(captor.getValue().get(0).getPuntos()));
    }
}
