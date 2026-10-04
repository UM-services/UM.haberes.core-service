package um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.dto.DependenciaResponse;

@Component
public class DependenciaDtoMapper {

    public DependenciaResponse toResponse(Dependencia domain) {
        if (domain == null) {
            return null;
        }
        return DependenciaResponse.builder()
                .dependenciaId(domain.getDependenciaId())
                .nombre(domain.getNombre())
                .acronimo(domain.getAcronimo())
                .facultadId(domain.getFacultadId())
                .geograficaId(domain.getGeograficaId())
                .build();
    }
}
