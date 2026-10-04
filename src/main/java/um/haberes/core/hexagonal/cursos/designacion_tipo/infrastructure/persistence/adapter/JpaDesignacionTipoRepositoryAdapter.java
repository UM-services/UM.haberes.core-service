package um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.persistence.adapter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.model.DesignacionTipo;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.ports.out.DesignacionTipoRepository;
import um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.persistence.mapper.DesignacionTipoMapper;
import um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.persistence.repository.JpaDesignacionTipoRepository;

@Component
@RequiredArgsConstructor
public class JpaDesignacionTipoRepositoryAdapter implements DesignacionTipoRepository {

    private final JpaDesignacionTipoRepository jpaDesignacionTipoRepository;
    private final DesignacionTipoMapper designacionTipoMapper;

    @Override
    public List<DesignacionTipo> findAll() {
        return jpaDesignacionTipoRepository.findAll().stream()
                .map(designacionTipoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DesignacionTipo> findByDesignacionTipoId(Integer designacionTipoId) {
        return jpaDesignacionTipoRepository.findByDesignacionTipoId(designacionTipoId)
                .map(designacionTipoMapper::toDomain);
    }

    @Override
    public Optional<DesignacionTipo> findFirstByHorasSemanalesGreaterThanEqual(BigDecimal horasSemanales) {
        return jpaDesignacionTipoRepository.findFirstByHorasSemanalesGreaterThanEqual(horasSemanales)
                .map(designacionTipoMapper::toDomain);
    }
}
