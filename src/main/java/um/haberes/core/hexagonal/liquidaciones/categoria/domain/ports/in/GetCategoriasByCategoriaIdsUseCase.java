package um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in;

import java.util.List;
import java.util.Set;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;

public interface GetCategoriasByCategoriaIdsUseCase {

    List<Categoria> getCategoriasByCategoriaIds(Set<Integer> categoriaIds);
}
