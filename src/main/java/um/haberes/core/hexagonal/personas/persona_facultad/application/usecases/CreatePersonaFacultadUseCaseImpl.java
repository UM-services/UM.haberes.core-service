package um.haberes.core.hexagonal.personas.persona_facultad.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona_facultad.application.exception.PersonaFacultadException;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.model.PersonaFacultad;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.in.CreatePersonaFacultadUseCase;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.out.PersonaFacultadRepository;

@Component
@RequiredArgsConstructor
public class CreatePersonaFacultadUseCaseImpl implements CreatePersonaFacultadUseCase {

    private final PersonaFacultadRepository personaFacultadRepository;

    @Override
    public PersonaFacultad createPersonaFacultad(PersonaFacultad personaFacultad) {
        if (personaFacultad == null || personaFacultad.getLegajoId() == null
                || personaFacultad.getFacultadId() == null) {
            throw PersonaFacultadException.missingData();
        }
        personaFacultadRepository
                .findByLegajoIdAndFacultadId(personaFacultad.getLegajoId(), personaFacultad.getFacultadId())
                .ifPresent(existing -> {
                    throw PersonaFacultadException.alreadyExists(existing.getLegajoId(), existing.getFacultadId());
                });
        return personaFacultadRepository.save(personaFacultad);
    }
}
