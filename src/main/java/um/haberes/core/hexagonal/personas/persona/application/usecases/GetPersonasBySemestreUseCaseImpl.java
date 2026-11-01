package um.haberes.core.hexagonal.personas.persona.application.usecases;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonasBySemestreUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;

@Component
@RequiredArgsConstructor
public class GetPersonasBySemestreUseCaseImpl implements GetPersonasBySemestreUseCase {

    private final PersonaRepository personaRepository;

    private final LiquidacionRepository liquidacionRepository;

    @Override
    public List<Persona> getPersonasBySemestre(Integer anho, Integer semestre) {
        List<Long> legajoIds = liquidacionRepository
                .findAllByAnhoAndMesBetween(anho, (semestre - 1) * 6 + 1, semestre * 6).stream()
                .map(Liquidacion::getLegajoId)
                .collect(Collectors.toList());
        return personaRepository.findAllByLegajoIdsOrderedByLegajoId(legajoIds);
    }
}
