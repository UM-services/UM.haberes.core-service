package um.haberes.core.hexagonal.personas.persona.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona.application.exception.PersonaException;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.model.PersonaPatch;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.PartialUpdatePersonaUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;

@Component
@RequiredArgsConstructor
public class PartialUpdatePersonaUseCaseImpl implements PartialUpdatePersonaUseCase {

    private final PersonaRepository personaRepository;

    @Override
    public Persona partialUpdatePersona(Long legajoId, PersonaPatch patch) {
        Persona persona = personaRepository.findByLegajoId(legajoId)
                .orElseThrow(() -> new PersonaException(legajoId));

        patch.applyTo(persona);

        return personaRepository.update(legajoId, persona)
                .orElseThrow(() -> new PersonaException(legajoId));
    }
}
