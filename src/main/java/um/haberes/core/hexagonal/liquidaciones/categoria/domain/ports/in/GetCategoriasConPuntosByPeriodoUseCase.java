package um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;

/**
 * Lectura de categorías con los valores ETEC del período ({@code puntos} y {@code etec_horas}),
 * con fallback a la plantilla de {@code categoria} cuando el período no tiene fila.
 */
public interface GetCategoriasConPuntosByPeriodoUseCase {

    List<Categoria> findAllByPeriodo(Integer anho, Integer mes);

    List<Categoria> getCategoriasConPuntos(Integer anho, Integer mes);

    Optional<Categoria> findByCategoriaIdAndPeriodo(Integer categoriaId, Integer anho, Integer mes);
}
