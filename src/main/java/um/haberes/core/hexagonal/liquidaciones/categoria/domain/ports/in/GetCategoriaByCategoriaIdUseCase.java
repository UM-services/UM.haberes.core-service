package um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;

public interface GetCategoriaByCategoriaIdUseCase {

    Optional<Categoria> getCategoriaByCategoriaId(Integer categoriaId);
}
