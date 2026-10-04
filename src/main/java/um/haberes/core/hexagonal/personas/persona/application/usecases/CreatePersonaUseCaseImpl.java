package um.haberes.core.hexagonal.personas.persona.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.CreatePersonaUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;

@Component
@RequiredArgsConstructor
public class CreatePersonaUseCaseImpl implements CreatePersonaUseCase {

    private final PersonaRepository personaRepository;

    @Override
    public Persona createPersona(Persona persona) {
        return personaRepository.save(persona);
    }
}
