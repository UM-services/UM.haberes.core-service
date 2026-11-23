package um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaFilter;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaPage;

public interface FindCategoriasByFilterUseCase {

    CategoriaPage findCategoriasByFilter(CategoriaFilter filter);
}
