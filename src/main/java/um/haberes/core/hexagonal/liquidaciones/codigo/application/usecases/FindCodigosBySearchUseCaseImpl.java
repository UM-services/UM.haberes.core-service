package um.haberes.core.hexagonal.liquidaciones.codigo.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.CodigoSearchResult;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.in.FindCodigosBySearchUseCase;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.out.CodigoSearchRepository;

@Component
@RequiredArgsConstructor
public class FindCodigosBySearchUseCaseImpl implements FindCodigosBySearchUseCase {

    private final CodigoSearchRepository codigoSearchRepository;

    @Override
    public List<CodigoSearchResult> findCodigosBySearch(String chain) {
        return codigoSearchRepository.findBySearch(chain);
    }
}
