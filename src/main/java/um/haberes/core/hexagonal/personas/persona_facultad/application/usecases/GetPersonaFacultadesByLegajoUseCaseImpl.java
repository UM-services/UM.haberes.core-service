package um.haberes.core.hexagonal.personas.persona_facultad.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.model.PersonaFacultad;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.in.GetPersonaFacultadesByLegajoUseCase;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.out.PersonaFacultadRepository;

@Component
@RequiredArgsConstructor
public class GetPersonaFacultadesByLegajoUseCaseImpl implements GetPersonaFacultadesByLegajoUseCase {

    private final PersonaFacultadRepository personaFacultadRepository;

    @Override
    public List<PersonaFacultad> getPersonaFacultadesByLegajo(Long legajoId) {
        return personaFacultadRepository.findAllByLegajoId(legajoId);
    }
}
