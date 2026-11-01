package um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.persistence.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.CodigoSearchResult;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.ports.out.CodigoSearchRepository;
import um.haberes.core.repository.view.JpaCodigoSearchRepository;

@Component
@RequiredArgsConstructor
public class JpaCodigoSearchRepositoryAdapter implements CodigoSearchRepository {

    private final JpaCodigoSearchRepository jpaCodigoSearchRepository;

    @Override
    public List<CodigoSearchResult> findBySearch(String chain) {
        return jpaCodigoSearchRepository.findTop50BySearchLikeOrderByNombre("%" + chain + "%").stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private CodigoSearchResult toDomain(um.haberes.core.model.view.CodigoSearch view) {
        if (view == null) {
            return null;
        }
        return CodigoSearchResult.builder()
                .codigoId(view.getCodigoId())
                .nombre(view.getNombre())
                .docente(view.getDocente())
                .noDocente(view.getNoDocente())
                .search(view.getSearch())
                .build();
    }
}
