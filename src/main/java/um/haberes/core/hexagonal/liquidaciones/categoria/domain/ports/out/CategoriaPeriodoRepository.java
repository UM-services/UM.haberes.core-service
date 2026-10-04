package um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;

public interface CategoriaPeriodoRepository {

    void upsert(Categoria categoria, Integer anho, Integer mes);

    void upsertAll(List<Categoria> categorias, Integer anho, Integer mes);

    List<Integer> findNoDocenteCategoriaIdsByPeriodo(Integer anho, Integer mes);
}
