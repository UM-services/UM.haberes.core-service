package um.haberes.core.hexagonal.personas.persona.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonasOrderByDependenciaUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;

@Component
@RequiredArgsConstructor
public class GetPersonasOrderByDependenciaUseCaseImpl implements GetPersonasOrderByDependenciaUseCase {

    private final PersonaRepository personaRepository;

    @Override
    public List<Persona> getPersonasOrderByDependencia() {
        return personaRepository.findAllOrderByDependencia();
    }
}
