package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaFilter;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaPage;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CondicionCategoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.entity.CategoriaEntity;

@SpringBootTest
@Transactional
class JpaCategoriaFilterRepositoryAdapterTest {

    @Autowired
    private JpaCategoriaFilterRepositoryAdapter adapter;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void cargarCatalogo() {
        guardar(1, "TITULAR", (byte) 1, (byte) 0, (byte) 0, BigDecimal.ZERO);
        guardar(2, "AUXILIAR 1", (byte) 1, (byte) 0, (byte) 0, BigDecimal.ZERO);
        guardar(1301, "DIRECTOR ETEC", (byte) 1, (byte) 0, (byte) 0, new BigDecimal("120"));
        guardar(1307, "AUXILIAR 2", (byte) 0, (byte) 1, (byte) 0, BigDecimal.ZERO);
        entityManager.flush();
    }

    @Test
    void buscaPorNombreSinImportarMayusculas() {
        CategoriaPage page = adapter.findByFilter(filtro("auxiliar", 0, 50));

        assertEquals(2, page.getTotal());
        assertEquals(List.of(2, 1307), ids(page));
    }

    @Test
    void buscaPorSubcadenaDeId() {
        CategoriaPage page = adapter.findByFilter(filtro("130", 0, 50));

        assertEquals(2, page.getTotal());
        assertEquals(List.of(1301, 1307), ids(page));
    }

    @Test
    void combinaLasPalabrasConAnd() {
        CategoriaPage page = adapter.findByFilter(filtro("aux 1", 0, 50));

        assertEquals(1, page.getTotal());
        assertEquals(List.of(2), ids(page));
    }

    @Test
    void sinTextoNiCondicionesDevuelveTodoOrdenadoYConTotal() {
        CategoriaPage page = adapter.findByFilter(filtro("", 0, 50));

        assertEquals(4, page.getTotal());
        assertEquals(List.of(1, 2, 1301, 1307), ids(page));
    }

    @Test
    void paginaLosResultadosYDevuelveElTotalCompleto() {
        CategoriaPage primera = adapter.findByFilter(filtro("", 0, 2));
        CategoriaPage segunda = adapter.findByFilter(filtro("", 1, 2));

        assertEquals(4, primera.getTotal());
        assertEquals(List.of(1, 2), ids(primera));
        assertEquals(4, segunda.getTotal());
        assertEquals(List.of(1301, 1307), ids(segunda));
        assertEquals(1, segunda.getPagina());
        assertEquals(2, segunda.getTamano());
    }

    @Test
    void filtraCargosEtecPorPuntos() {
        CategoriaPage page = adapter.findByFilter(CategoriaFilter.builder()
                .condiciones(List.of(CondicionCategoria.ETEC))
                .build());

        assertEquals(1, page.getTotal());
        assertEquals(List.of(1301), ids(page));
    }

    @Test
    void combinaTextoYCondicionesConAnd() {
        CategoriaPage page = adapter.findByFilter(CategoriaFilter.builder()
                .texto("aux")
                .condiciones(List.of(CondicionCategoria.NO_DOCENTE))
                .build());

        assertEquals(1, page.getTotal());
        assertEquals(List.of(1307), ids(page));
    }

    private CategoriaFilter filtro(String texto, int pagina, int tamano) {
        return CategoriaFilter.builder()
                .texto(texto)
                .pagina(pagina)
                .tamano(tamano)
                .build();
    }

    private void guardar(int categoriaId, String nombre, byte docente, byte noDocente, byte porHora,
            BigDecimal puntos) {
        CategoriaEntity entity = new CategoriaEntity();
        entity.setCategoriaId(categoriaId);
        entity.setNombre(nombre);
        entity.setBasico(new BigDecimal("100"));
        entity.setDocente(docente);
        entity.setNoDocente(noDocente);
        entity.setLiquidaPorHora(porHora);
        entity.setEstadoDocente(BigDecimal.ZERO);
        entity.setPuntos(puntos);
        entity.setCreated(LocalDateTime.now());
        entityManager.persist(entity);
    }

    private List<Integer> ids(CategoriaPage page) {
        return page.getFilas().stream().map(Categoria::getCategoriaId).toList();
    }
}
