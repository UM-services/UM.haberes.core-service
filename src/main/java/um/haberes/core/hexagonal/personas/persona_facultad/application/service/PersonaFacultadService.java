package um.haberes.core.hexagonal.personas.persona_facultad.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.model.PersonaFacultad;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.in.CreatePersonaFacultadUseCase;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.in.DeletePersonaFacultadByUniqueUseCase;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.in.GetPersonaFacultadesByFacultadUseCase;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.in.GetPersonaFacultadesByLegajoUseCase;

@Service
@RequiredArgsConstructor
public class PersonaFacultadService {

    private final GetPersonaFacultadesByFacultadUseCase getPersonaFacultadesByFacultadUseCase;
    private final GetPersonaFacultadesByLegajoUseCase getPersonaFacultadesByLegajoUseCase;
    private final CreatePersonaFacultadUseCase createPersonaFacultadUseCase;
    private final DeletePersonaFacultadByUniqueUseCase deletePersonaFacultadByUniqueUseCase;

    public List<PersonaFacultad> findAllByFacultad(Integer facultadId) {
        return getPersonaFacultadesByFacultadUseCase.getPersonaFacultadesByFacultad(facultadId);
    }

    public List<PersonaFacultad> findAllByPersona(Long legajoId) {
        return getPersonaFacultadesByLegajoUseCase.getPersonaFacultadesByLegajo(legajoId);
    }

    public PersonaFacultad add(PersonaFacultad personaFacultad) {
        return createPersonaFacultadUseCase.createPersonaFacultad(personaFacultad);
    }

    public void deleteByUnique(Long legajoId, Integer facultadId) {
        deletePersonaFacultadByUniqueUseCase.deletePersonaFacultadByUnique(legajoId, facultadId);
    }
}
