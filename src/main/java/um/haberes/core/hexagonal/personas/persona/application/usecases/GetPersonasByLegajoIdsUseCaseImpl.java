package um.haberes.core.hexagonal.personas.persona.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonasByLegajoIdsUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;

@Component
@RequiredArgsConstructor
public class GetPersonasByLegajoIdsUseCaseImpl implements GetPersonasByLegajoIdsUseCase {

    private final PersonaRepository personaRepository;

    @Override
    public List<Persona> getPersonasByLegajoIds(List<Long> legajoIds) {
        return personaRepository.findAllByLegajoIdsOrderedByNombre(legajoIds);
    }
}
