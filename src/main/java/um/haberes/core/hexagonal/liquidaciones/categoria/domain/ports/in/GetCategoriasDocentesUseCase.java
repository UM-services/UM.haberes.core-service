package um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;

public interface GetCategoriasDocentesUseCase {

    List<Categoria> getCategoriasDocentes();
}
