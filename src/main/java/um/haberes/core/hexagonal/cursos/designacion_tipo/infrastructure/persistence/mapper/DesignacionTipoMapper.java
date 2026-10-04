package um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.model.DesignacionTipo;
import um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.persistence.entity.DesignacionTipoEntity;

@Component
public class DesignacionTipoMapper {

    public DesignacionTipoEntity toEntity(DesignacionTipo domain) {
        if (domain == null) {
            return null;
        }
        DesignacionTipoEntity entity = new DesignacionTipoEntity();
        if (domain.getDesignacionTipoId() != null) {
            entity.setDesignacionTipoId(domain.getDesignacionTipoId());
        }
        if (domain.getNombre() != null) {
            entity.setNombre(domain.getNombre());
        }
        if (domain.getHorasSemanales() != null) {
            entity.setHorasSemanales(domain.getHorasSemanales());
        }
        if (domain.getHorasTotales() != null) {
            entity.setHorasTotales(domain.getHorasTotales());
        }
        if (domain.getSimples() != null) {
            entity.setSimples(domain.getSimples());
        }
        return entity;
    }

    public DesignacionTipo toDomain(DesignacionTipoEntity entity) {
        if (entity == null) {
            return null;
        }
        return DesignacionTipo.builder()
                .designacionTipoId(entity.getDesignacionTipoId())
                .nombre(entity.getNombre())
                .horasSemanales(entity.getHorasSemanales())
                .horasTotales(entity.getHorasTotales())
                .simples(entity.getSimples())
                .build();
    }
}
