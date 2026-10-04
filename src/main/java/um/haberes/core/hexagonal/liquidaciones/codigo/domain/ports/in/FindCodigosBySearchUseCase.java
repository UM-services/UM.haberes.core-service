package um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.CodigoSearchResult;

public interface FindCodigosBySearchUseCase {

    List<CodigoSearchResult> findCodigosBySearch(String chain);
}
