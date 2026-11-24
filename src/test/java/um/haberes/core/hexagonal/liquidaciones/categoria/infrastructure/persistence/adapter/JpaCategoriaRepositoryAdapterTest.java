package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.entity.CategoriaEntity;
import um.haberes.core.model.CategoriaPeriodoEntity;
import um.haberes.core.repository.JpaCategoriaPeriodoRepository;

@SpringBootTest
@Transactional
class JpaCategoriaRepositoryAdapterTest {

    private static final Integer ANHO = 2026;

    private static final Integer MES = 11;

    @Autowired
    private JpaCategoriaRepositoryAdapter adapter;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JpaCategoriaPeriodoRepository jpaCategoriaPeriodoRepository;

    @BeforeEach
    void cargarCatalogo() {
        guardarCategoria(992, "DIRECTOR ETEC", new BigDecimal("1738.00"), (byte) 0);
        guardarCategoria(993, "VICE DIRECTOR ETEC", new BigDecimal("1469.00"), (byte) 0);
        entityManager.flush();
    }

    @Test
    void usaElPuntajeYElFlagDelPeriodoCuandoExisteLaFila() {
        guardarPeriodo(992, new BigDecimal("1500.00"), (byte) 1);

        List<Categoria> categorias = adapter.findAllByPeriodo(ANHO, MES);

        Categoria director = buscar(categorias, 992);
        assertEquals(0, new BigDecimal("1500.00").compareTo(director.getPuntos()));
        assertEquals((byte) 1, director.getEtecHoras());
    }

    @Test
    void caeEnLaPlantillaCuandoElPeriodoNoTieneFila() {
        guardarPeriodo(992, new BigDecimal("1500.00"), (byte) 1);

        Categoria vice = buscar(adapter.findAllByPeriodo(ANHO, MES), 993);

        assertEquals(0, new BigDecimal("1469.00").compareTo(vice.getPuntos()));
        assertEquals((byte) 0, vice.getEtecHoras());
    }

    @Test
    void heredaLaPlantillaYRegistraElPuntajeCuandoLaFilaDelPeriodoEstaEnCero() {
        guardarPeriodo(992, BigDecimal.ZERO, (byte) 0);

        Categoria director = buscar(adapter.findAllByPeriodo(ANHO, MES), 992);

        assertEquals(0, new BigDecimal("1738.00").compareTo(director.getPuntos()));
        assertEquals(0, new BigDecimal("1738.00").compareTo(periodo(992).getPuntos()));
    }

    @Test
    void materializaLaFilaDelPeriodoCuandoNoExiste() {
        adapter.findAllByPeriodo(ANHO, MES);

        CategoriaPeriodoEntity vice = periodo(993);
        assertNotNull(vice);
        assertEquals(0, new BigDecimal("1469.00").compareTo(vice.getPuntos()));
        assertEquals((byte) 1, vice.getDocente());
    }

    @Test
    void noMaterializaCategoriasSinDatosEtec() {
        guardarCategoria(994, "CATEGORIA COMUN", BigDecimal.ZERO, (byte) 0);

        adapter.findAllByPeriodo(ANHO, MES);

        assertNull(periodo(994));
    }

    @Test
    void buscarPorIdYPeriodoMaterializaLaFilaCuandoNoExiste() {
        Categoria vice = adapter.findByCategoriaIdAndPeriodo(993, ANHO, MES).orElseThrow();

        assertEquals(0, new BigDecimal("1469.00").compareTo(vice.getPuntos()));
        assertEquals(0, new BigDecimal("1469.00").compareTo(periodo(993).getPuntos()));
    }

    @Test
    void buscarPorIdYPeriodoAplicaElOverlay() {
        guardarPeriodo(992, new BigDecimal("1500.00"), (byte) 1);

        Categoria director = adapter.findByCategoriaIdAndPeriodo(992, ANHO, MES).orElseThrow();

        assertEquals(0, new BigDecimal("1500.00").compareTo(director.getPuntos()));
        assertEquals((byte) 1, director.getEtecHoras());
    }

    private void guardarCategoria(int categoriaId, String nombre, BigDecimal puntos, byte etecHoras) {
        CategoriaEntity entity = new CategoriaEntity();
        entity.setCategoriaId(categoriaId);
        entity.setNombre(nombre);
        entity.setBasico(BigDecimal.ZERO);
        entity.setDocente((byte) 1);
        entity.setNoDocente((byte) 0);
        entity.setLiquidaPorHora((byte) 0);
        entity.setEstadoDocente(BigDecimal.ZERO);
        entity.setPuntos(puntos);
        entity.setEtecHoras(etecHoras);
        entity.setCreated(LocalDateTime.now());
        entityManager.persist(entity);
        entityManager.flush();
    }

    private void guardarPeriodo(int categoriaId, BigDecimal puntos, byte etecHoras) {
        CategoriaPeriodoEntity entity = new CategoriaPeriodoEntity();
        entity.setCategoriaId(categoriaId);
        entity.setAnho(ANHO);
        entity.setMes(MES);
        entity.setNombre("DIRECTOR ETEC");
        entity.setBasico(BigDecimal.ZERO);
        entity.setDocente((byte) 1);
        entity.setNoDocente((byte) 0);
        entity.setLiquidaPorHora((byte) 0);
        entity.setEstadoDocente(BigDecimal.ZERO);
        entity.setPuntos(puntos);
        entity.setEtecHoras(etecHoras);
        entity.setCreated(LocalDateTime.now());
        entityManager.persist(entity);
        entityManager.flush();
    }

    private Categoria buscar(List<Categoria> categorias, int categoriaId) {
        return categorias.stream()
                .filter(categoria -> categoria.getCategoriaId() == categoriaId)
                .findFirst()
                .orElseThrow();
    }

    private CategoriaPeriodoEntity periodo(int categoriaId) {
        return jpaCategoriaPeriodoRepository.findByCategoriaIdAndAnhoAndMes(categoriaId, ANHO, MES).orElse(null);
    }
}
