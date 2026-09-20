package um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;

public interface CreateCategoriaUseCase {

    Categoria createCategoria(Categoria categoria, Integer anho, Integer mes);
}
