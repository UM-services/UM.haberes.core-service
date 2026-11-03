package um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.facultad.infrastructure.web.mapper.FacultadDtoMapper;
import um.haberes.core.hexagonal.geografica.infrastructure.web.mapper.GeograficaDtoMapper;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.dto.DependenciaResponse;

@Component
@RequiredArgsConstructor
public class DependenciaDtoMapper {

    private final FacultadDtoMapper facultadDtoMapper;

    private final GeograficaDtoMapper geograficaDtoMapper;

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
                .facultad(facultadDtoMapper.toResponse(domain.getFacultad()))
                .geografica(geograficaDtoMapper.toResponse(domain.getGeografica()))
                .build();
    }
}
