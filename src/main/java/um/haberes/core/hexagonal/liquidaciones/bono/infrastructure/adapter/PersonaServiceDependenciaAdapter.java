package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.PersonaQueryPort;
import um.haberes.core.hexagonal.personas.persona.application.exception.PersonaException;
import um.haberes.core.hexagonal.personas.persona.application.service.PersonaService;

@Component
@RequiredArgsConstructor
public class PersonaServiceDependenciaAdapter implements PersonaQueryPort {

    private final PersonaService personaService;

    @Override
    public Optional<Integer> findDependenciaIdByLegajoId(Long legajoId) {
        try {
            return Optional.ofNullable(personaService.findByLegajoId(legajoId).getDependenciaId());
        } catch (PersonaException e) {
            return Optional.empty();
        }
    }
}
