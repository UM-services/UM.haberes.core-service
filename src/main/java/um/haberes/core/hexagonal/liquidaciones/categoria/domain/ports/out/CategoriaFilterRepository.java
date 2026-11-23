package um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaFilter;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaPage;

public interface CategoriaFilterRepository {

    CategoriaPage findByFilter(CategoriaFilter filter);
}
