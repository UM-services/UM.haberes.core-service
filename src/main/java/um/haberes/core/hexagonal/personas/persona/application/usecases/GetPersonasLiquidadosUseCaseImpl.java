package um.haberes.core.hexagonal.personas.persona.application.usecases;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonasLiquidadosUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;

@Component
@RequiredArgsConstructor
public class GetPersonasLiquidadosUseCaseImpl implements GetPersonasLiquidadosUseCase {

    private static final int SIN_LIMITE = 0;

    private final PersonaRepository personaRepository;

    private final LiquidacionRepository liquidacionRepository;

    @Override
    public List<Persona> getPersonasLiquidados(Integer anho, Integer mes) {
        List<Long> legajoIds = liquidacionRepository.findAllByAnhoAndMes(anho, mes, SIN_LIMITE).stream()
                .map(Liquidacion::getLegajoId)
                .collect(Collectors.toList());
        return personaRepository.findAllByLegajoIdsOrderedByNombre(legajoIds);
    }
}
