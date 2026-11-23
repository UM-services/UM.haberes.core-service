package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.UniversoCargos;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
class CatalogoUniversosCargosTest {

    private static final Integer ANHO = 2026;

    private static final Integer MES = 11;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CatalogoUniversosCargos catalogo;

    private Categoria categoria(Integer id, String puntos, byte docente, byte noDocente) {
        return Categoria.builder()
                .categoriaId(id)
                .docente(docente)
                .noDocente(noDocente)
                .puntos(new BigDecimal(puntos))
                .build();
    }

    private Categoria categoriaHoras(Integer id) {
        return Categoria.builder()
                .categoriaId(id)
                .docente((byte) 1)
                .puntos(BigDecimal.ZERO)
                .etecHoras((byte) 1)
                .build();
    }

    @Test
    void elUniversoDocenteExcluyeLasCategoriasConPuntos() {
        when(categoriaRepository.findAllByPeriodo(ANHO, MES)).thenReturn(List.of(
                categoria(1, "0.00", (byte) 1, (byte) 0),
                categoria(980, "0.00", (byte) 1, (byte) 0),
                categoria(2001, "3000.00", (byte) 1, (byte) 0)));

        assertThat(catalogo.categoriaIds(UniversoCargos.DOCENTE, ANHO, MES)).containsExactly(1, 980);
    }

    @Test
    void elUniversoNoDocenteExcluyeLasCategoriasConPuntos() {
        when(categoriaRepository.findAllByPeriodo(ANHO, MES)).thenReturn(List.of(
                categoria(5, "0.00", (byte) 0, (byte) 1),
                categoria(2001, "100.00", (byte) 0, (byte) 1)));

        assertThat(catalogo.categoriaIds(UniversoCargos.NO_DOCENTE, ANHO, MES)).containsExactly(5);
    }

    @Test
    void elUniversoDocenteExcluyeLaCategoriaDeHorasEtec() {
        when(categoriaRepository.findAllByPeriodo(ANHO, MES)).thenReturn(List.of(
                categoria(1, "0.00", (byte) 1, (byte) 0),
                categoriaHoras(2002)));

        assertThat(catalogo.categoriaIds(UniversoCargos.DOCENTE, ANHO, MES)).containsExactly(1);
    }

    @Test
    void elUniversoEtecSonLasCategoriasConPuntosMasLasDeHoras() {
        when(categoriaRepository.findAllByPeriodo(ANHO, MES)).thenReturn(List.of(
                categoria(2001, "3000.00", (byte) 1, (byte) 0),
                categoriaHoras(2002)));

        assertThat(catalogo.categoriaIds(UniversoCargos.ETEC, ANHO, MES)).containsExactly(2001, 2002);
    }

    @Test
    void uneLosAlcancesSinRepetirCategorias() {
        when(categoriaRepository.findAllByPeriodo(ANHO, MES)).thenReturn(List.of(
                categoria(1, "0.00", (byte) 1, (byte) 0),
                categoria(2001, "3000.00", (byte) 1, (byte) 0),
                categoriaHoras(2002)));

        assertThat(catalogo.categoriaIds(Set.of(UniversoCargos.DOCENTE, UniversoCargos.ETEC), List.of(2001),
                ANHO, MES))
                .containsExactlyInAnyOrder(1, 2001, 2002);
    }

    @Test
    void clasificaConLosValoresDelPeriodo() {
        when(categoriaRepository.findAllByPeriodo(ANHO, MES)).thenReturn(List.of(
                categoria(2001, "1738.00", (byte) 1, (byte) 0),
                categoria(2003, "0.00", (byte) 1, (byte) 0)));

        assertThat(catalogo.categoriaIds(UniversoCargos.ETEC, ANHO, MES)).containsExactly(2001);
        assertThat(catalogo.categoriaIds(UniversoCargos.DOCENTE, ANHO, MES)).containsExactly(2003);
    }
}
