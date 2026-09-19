package um.haberes.core.hexagonal.personas.persona.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonasLiquidablesUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;

@Component
@RequiredArgsConstructor
public class GetPersonasLiquidablesUseCaseImpl implements GetPersonasLiquidablesUseCase {

    private final PersonaRepository personaRepository;

    @Override
    public List<Persona> getPersonasLiquidables() {
        return personaRepository.findAllLiquidables();
    }
}
