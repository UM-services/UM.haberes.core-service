package um.haberes.core.hexagonal.personas.persona.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonasByDesarraigoUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.CursoCargoRepository;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;

@Component
@RequiredArgsConstructor
public class GetPersonasByDesarraigoUseCaseImpl implements GetPersonasByDesarraigoUseCase {

    private static final byte DESARRAIGO_ACTIVO = (byte) 1;

    private final PersonaRepository personaRepository;

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public List<Persona> getPersonasByDesarraigo(Integer anho, Integer mes) {
        List<Long> legajoIds = cursoCargoRepository.findLegajoIdsByAnhoAndMesAndDesarraigo(anho, mes,
                DESARRAIGO_ACTIVO);
        return personaRepository.findAllByLegajoIdsOrderedByNombre(legajoIds);
    }
}
