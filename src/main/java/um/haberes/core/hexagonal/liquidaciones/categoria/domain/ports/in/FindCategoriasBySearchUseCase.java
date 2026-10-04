package um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaSearchResult;

public interface FindCategoriasBySearchUseCase {

    List<CategoriaSearchResult> findCategoriasBySearch(String chain);
}
