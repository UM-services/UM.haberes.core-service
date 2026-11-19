package um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.mapper.FacultadMapper;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.mapper.PersonaMapper;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.model.PersonaFacultad;
import um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.persistence.entity.PersonaFacultadEntity;

@Component
@RequiredArgsConstructor
public class PersonaFacultadMapper {

    private final PersonaMapper personaMapper;

    private final FacultadMapper facultadMapper;

    public PersonaFacultadEntity toEntity(PersonaFacultad domain) {
        if (domain == null) {
            return null;
        }
        return PersonaFacultadEntity.builder()
                .personaFacultadId(domain.getPersonaFacultadId())
                .legajoId(domain.getLegajoId())
                .facultadId(domain.getFacultadId())
                .build();
    }

    public PersonaFacultad toDomain(PersonaFacultadEntity entity) {
        if (entity == null) {
            return null;
        }
        return PersonaFacultad.builder()
                .personaFacultadId(entity.getPersonaFacultadId())
                .legajoId(entity.getLegajoId())
                .facultadId(entity.getFacultadId())
                .persona(personaMapper.toDomain(entity.getPersona()))
                .facultad(facultadMapper.toDomainModel(entity.getFacultad()))
                .build();
    }
}
