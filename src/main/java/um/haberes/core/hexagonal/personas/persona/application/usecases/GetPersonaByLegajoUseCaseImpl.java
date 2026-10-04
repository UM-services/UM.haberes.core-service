package um.haberes.core.hexagonal.personas.persona.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonaByLegajoUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;

@Component
@RequiredArgsConstructor
public class GetPersonaByLegajoUseCaseImpl implements GetPersonaByLegajoUseCase {

    private final PersonaRepository personaRepository;

    @Override
    public Optional<Persona> getPersonaByLegajo(Long legajoId) {
        return personaRepository.findByLegajoId(legajoId);
    }
}
