package um.haberes.core.hexagonal.liquidaciones.item.infrastructure.persistence.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.CodigoGrupoRepository;
import um.haberes.core.model.CodigoGrupoEntity;
import um.haberes.core.repository.JpaCodigoGrupoRepository;

@Component
@RequiredArgsConstructor
public class JpaCodigoGrupoRepositoryAdapter implements CodigoGrupoRepository {

    private final JpaCodigoGrupoRepository jpaCodigoGrupoRepository;

    @Override
    public List<Integer> findCodigoIdsByRemunerativo(Byte remunerativo) {
        return jpaCodigoGrupoRepository.findAllByRemunerativoOrderByCodigoId(remunerativo).stream()
                .map(CodigoGrupoEntity::getCodigoId)
                .collect(Collectors.toList());
    }
}
