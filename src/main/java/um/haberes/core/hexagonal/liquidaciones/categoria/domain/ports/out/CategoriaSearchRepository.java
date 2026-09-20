package um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaSearchResult;

public interface CategoriaSearchRepository {

    List<CategoriaSearchResult> findBySearch(String chain);
}
