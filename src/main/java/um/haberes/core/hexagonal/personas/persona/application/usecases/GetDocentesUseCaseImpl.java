package um.haberes.core.hexagonal.personas.persona.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetDocentesUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.CursoCargoRepository;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;

@Component
@RequiredArgsConstructor
public class GetDocentesUseCaseImpl implements GetDocentesUseCase {

    private final PersonaRepository personaRepository;

    private final CursoCargoRepository cursoCargoRepository;

    @Override
    public List<Persona> getDocentes(Integer anho, Integer mes) {
        List<Long> legajoIds = cursoCargoRepository.findLegajoIdsByAnhoAndMes(anho, mes);
        return personaRepository.findAllByLegajoIdsOrderedByNombre(legajoIds);
    }
}
