package um.haberes.core.hexagonal.liquidaciones.categoria.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaSearchResult;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.in.FindCategoriasBySearchUseCase;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaSearchRepository;

@Component
@RequiredArgsConstructor
public class FindCategoriasBySearchUseCaseImpl implements FindCategoriasBySearchUseCase {

    private final CategoriaSearchRepository categoriaSearchRepository;

    @Override
    public List<CategoriaSearchResult> findCategoriasBySearch(String chain) {
        return categoriaSearchRepository.findBySearch(chain);
    }
}
