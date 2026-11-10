package um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.facultad.infrastructure.web.mapper.FacultadDtoMapper;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.mapper.PersonaDtoMapper;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.model.PersonaFacultad;
import um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.web.dto.PersonaFacultadRequest;
import um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.web.dto.PersonaFacultadResponse;

@Component
@RequiredArgsConstructor
public class PersonaFacultadDtoMapper {

    private final PersonaDtoMapper personaDtoMapper;

    private final FacultadDtoMapper facultadDtoMapper;

    public PersonaFacultad toDomain(PersonaFacultadRequest request) {
        if (request == null) {
            return null;
        }
        return PersonaFacultad.builder()
                .legajoId(request.getLegajoId())
                .facultadId(request.getFacultadId())
                .build();
    }

    public PersonaFacultadResponse toResponse(PersonaFacultad domain) {
        if (domain == null) {
            return null;
        }
        return PersonaFacultadResponse.builder()
                .personaFacultadId(domain.getPersonaFacultadId())
                .legajoId(domain.getLegajoId())
                .facultadId(domain.getFacultadId())
                .persona(personaDtoMapper.toResponse(domain.getPersona()))
                .facultad(facultadDtoMapper.toResponse(domain.getFacultad()))
                .build();
    }
}
