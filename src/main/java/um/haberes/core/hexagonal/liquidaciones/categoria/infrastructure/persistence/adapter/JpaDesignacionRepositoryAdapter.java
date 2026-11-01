package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.DesignacionRepository;
import um.haberes.core.model.DesignacionEntity;
import um.haberes.core.service.DesignacionService;

@Component
@RequiredArgsConstructor
public class JpaDesignacionRepositoryAdapter implements DesignacionRepository {

    private final DesignacionService designacionService;

    @Override
    public List<Integer> findCategoriaIdsAsignadas() {
        return designacionService.findAllAsignables().stream()
                .map(DesignacionEntity::getCategoriaId)
                .collect(Collectors.toList());
    }
}
