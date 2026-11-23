package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaFilter;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaPage;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CondicionCategoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaFilterRepository;

@ExtendWith(MockitoExtension.class)
class FindCategoriasByFilterUseCaseImplTest {

    @Mock
    private CategoriaFilterRepository categoriaFilterRepository;

    @InjectMocks
    private FindCategoriasByFilterUseCaseImpl useCase;

    private final CategoriaPage paginaVacia = CategoriaPage.builder()
            .filas(List.of())
            .total(0)
            .pagina(0)
            .tamano(50)
            .build();

    @Test
    void conFiltroNuloAplicaLosValoresPorDefecto() {
        when(categoriaFilterRepository.findByFilter(any())).thenReturn(paginaVacia);

        assertSame(paginaVacia, useCase.findCategoriasByFilter(null));

        CategoriaFilter enviado = capturarFiltro();
        assertEquals("", enviado.getTexto());
        assertEquals(List.of(), enviado.getCondiciones());
        assertEquals(0, enviado.getPagina());
        assertEquals(CategoriaFilter.TAMANO_POR_DEFECTO, enviado.getTamano());
    }

    @Test
    void recortaElTextoYCorrigePaginaNegativa() {
        when(categoriaFilterRepository.findByFilter(any())).thenReturn(paginaVacia);

        useCase.findCategoriasByFilter(CategoriaFilter.builder()
                .texto("  auxiliar 1  ")
                .pagina(-3)
                .tamano(20)
                .build());

        CategoriaFilter enviado = capturarFiltro();
        assertEquals("auxiliar 1", enviado.getTexto());
        assertEquals(0, enviado.getPagina());
        assertEquals(20, enviado.getTamano());
    }

    @Test
    void limitaElTamanoAlMaximoPermitido() {
        when(categoriaFilterRepository.findByFilter(any())).thenReturn(paginaVacia);

        useCase.findCategoriasByFilter(CategoriaFilter.builder().tamano(5_000).build());

        assertEquals(CategoriaFilter.TAMANO_MAXIMO, capturarFiltro().getTamano());
    }

    @Test
    void unTamanoNoPositivoVuelveAlValorPorDefecto() {
        when(categoriaFilterRepository.findByFilter(any())).thenReturn(paginaVacia);

        useCase.findCategoriasByFilter(CategoriaFilter.builder().tamano(0).build());

        assertEquals(CategoriaFilter.TAMANO_POR_DEFECTO, capturarFiltro().getTamano());
    }

    @Test
    void descartaCondicionesNulasYRepetidas() {
        when(categoriaFilterRepository.findByFilter(any())).thenReturn(paginaVacia);

        useCase.findCategoriasByFilter(CategoriaFilter.builder()
                .condiciones(Arrays.asList(CondicionCategoria.DOCENTE, null,
                        CondicionCategoria.DOCENTE, CondicionCategoria.ETEC))
                .build());

        assertEquals(List.of(CondicionCategoria.DOCENTE, CondicionCategoria.ETEC),
                capturarFiltro().getCondiciones());
    }

    private CategoriaFilter capturarFiltro() {
        ArgumentCaptor<CategoriaFilter> captor = ArgumentCaptor.forClass(CategoriaFilter.class);
        verify(categoriaFilterRepository).findByFilter(captor.capture());
        return captor.getValue();
    }
}
