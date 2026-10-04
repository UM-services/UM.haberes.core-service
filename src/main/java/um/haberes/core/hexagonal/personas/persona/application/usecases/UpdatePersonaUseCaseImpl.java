package um.haberes.core.hexagonal.personas.persona.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.UpdatePersonaUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;

@Component
@RequiredArgsConstructor
public class UpdatePersonaUseCaseImpl implements UpdatePersonaUseCase {

    private final PersonaRepository personaRepository;

    @Override
    public Optional<Persona> updatePersona(Long legajoId, Persona persona) {
        return personaRepository.update(legajoId, persona);
    }
}
