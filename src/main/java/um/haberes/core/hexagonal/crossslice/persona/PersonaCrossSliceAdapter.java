package um.haberes.core.hexagonal.crossslice.persona;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.crossslice.persona.model.PersonaView;
import um.haberes.core.hexagonal.personas.persona.application.exception.PersonaException;
import um.haberes.core.hexagonal.personas.persona.application.service.PersonaService;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;

/**
 * Adaptador cross-slice de persona: lee el slice dueño y lo proyecta a {@link PersonaView}.
 *
 * <p>Es el único punto que conoce el dominio de {@code personas/persona}.
 */
@Component
@RequiredArgsConstructor
public class PersonaCrossSliceAdapter implements PersonaCrossSlice {

    private static final String NO_LIQUIDA = "N";

    private final PersonaService personaService;

    @Override
    public Optional<PersonaView> porLegajo(Long legajoId) {
        try {
            return Optional.of(toView(personaService.findByLegajoId(legajoId)));
        } catch (PersonaException e) {
            return Optional.empty();
        }
    }

    @Override
    public void marcarNoLiquida(Long legajoId) {
        Persona persona = personaService.findByLegajoId(legajoId);
        if (!NO_LIQUIDA.equals(persona.getLiquida())) {
            persona.setLiquida(NO_LIQUIDA);
            personaService.update(persona, legajoId);
        }
    }

    private PersonaView toView(Persona persona) {
        if (persona == null) {
            return null;
        }
        return new PersonaView(persona.getLegajoId(), persona.getDocumento(), persona.getApellido(),
                persona.getNombre(), persona.getEstado(), persona.getLiquida(),
                persona.getDependenciaId(), persona.getSalida());
    }
}
