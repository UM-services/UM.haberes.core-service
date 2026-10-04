package um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;

public interface SaveAllCategoriasUseCase {

    List<Categoria> saveAllCategorias(List<Categoria> categorias, Integer anho, Integer mes);
}
