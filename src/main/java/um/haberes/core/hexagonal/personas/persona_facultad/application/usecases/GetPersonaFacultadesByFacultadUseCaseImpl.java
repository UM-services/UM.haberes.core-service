package um.haberes.core.hexagonal.personas.persona_facultad.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.model.PersonaFacultad;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.in.GetPersonaFacultadesByFacultadUseCase;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.out.PersonaFacultadRepository;

@Component
@RequiredArgsConstructor
public class GetPersonaFacultadesByFacultadUseCaseImpl implements GetPersonaFacultadesByFacultadUseCase {

    private final PersonaFacultadRepository personaFacultadRepository;

    @Override
    public List<PersonaFacultad> getPersonaFacultadesByFacultad(Integer facultadId) {
        return personaFacultadRepository.findAllByFacultadId(facultadId);
    }
}
