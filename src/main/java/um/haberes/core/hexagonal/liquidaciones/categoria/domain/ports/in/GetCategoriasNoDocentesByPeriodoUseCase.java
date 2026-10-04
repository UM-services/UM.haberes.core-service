package um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;

public interface GetCategoriasNoDocentesByPeriodoUseCase {

    List<Categoria> getCategoriasNoDocentesByPeriodo(Integer anho, Integer mes);
}
