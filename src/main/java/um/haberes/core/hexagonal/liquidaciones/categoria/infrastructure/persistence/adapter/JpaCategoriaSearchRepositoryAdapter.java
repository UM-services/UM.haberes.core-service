package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.CategoriaSearchResult;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaSearchRepository;
import um.haberes.core.model.view.CategoriaSearch;
import um.haberes.core.repository.view.JpaCategoriaSearchRepository;

@Component
@RequiredArgsConstructor
public class JpaCategoriaSearchRepositoryAdapter implements CategoriaSearchRepository {

    private final JpaCategoriaSearchRepository jpaCategoriaSearchRepository;

    @Override
    public List<CategoriaSearchResult> findBySearch(String chain) {
        return jpaCategoriaSearchRepository.findTop50BySearchLike("%" + chain + "%", Sort.by("nombre").ascending())
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private CategoriaSearchResult toDomain(CategoriaSearch view) {
        if (view == null) {
            return null;
        }
        return CategoriaSearchResult.builder()
                .categoriaId(view.getCategoriaId())
                .nombre(view.getNombre())
                .basico(view.getBasico())
                .search(view.getSearch())
                .build();
    }
}
