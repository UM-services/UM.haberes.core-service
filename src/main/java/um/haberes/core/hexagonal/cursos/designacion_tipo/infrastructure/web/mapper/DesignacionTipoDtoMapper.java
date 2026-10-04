package um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.model.DesignacionTipo;
import um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.web.dto.DesignacionTipoResponse;

@Component
public class DesignacionTipoDtoMapper {

    public DesignacionTipoResponse toResponse(DesignacionTipo domain) {
        if (domain == null) {
            return null;
        }
        return DesignacionTipoResponse.builder()
                .designacionTipoId(domain.getDesignacionTipoId())
                .nombre(domain.getNombre())
                .horasSemanales(domain.getHorasSemanales())
                .horasTotales(domain.getHorasTotales())
                .simples(domain.getSimples())
                .build();
    }
}
