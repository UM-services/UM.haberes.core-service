package um.haberes.core.hexagonal.personas.persona.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonasByFacultadUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.CursoCargoRepository;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.CursoRepository;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;

@Component
@RequiredArgsConstructor
public class GetPersonasByFacultadUseCaseImpl implements GetPersonasByFacultadUseCase {

    private final PersonaRepository personaRepository;

    private final CursoRepository cursoRepository;

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public List<Persona> getPersonasByFacultad(Integer facultadId) {
        List<Long> cursoIds = cursoRepository.findCursoIdsByFacultadId(facultadId);
        List<Long> legajoIds = cursoCargoRepository.findLegajoIdsByCursoIds(cursoIds);
        return personaRepository.findAllByLegajoIdsOrderedByNombre(legajoIds);
    }
}
