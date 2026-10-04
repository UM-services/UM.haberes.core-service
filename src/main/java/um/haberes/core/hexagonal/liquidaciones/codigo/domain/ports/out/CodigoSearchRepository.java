package um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.CodigoSearchResult;

public interface CodigoSearchRepository {

    List<CodigoSearchResult> findBySearch(String chain);
}
