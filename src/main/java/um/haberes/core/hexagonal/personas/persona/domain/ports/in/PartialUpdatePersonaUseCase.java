package um.haberes.core.hexagonal.personas.persona.domain.ports.in;

import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.model.PersonaPatch;

public interface PartialUpdatePersonaUseCase {

    /**
     * Aplica sólo los campos informados en el patch y devuelve la persona
     * resultante. Falla si el legajo no existe.
     */
    Persona partialUpdatePersona(Long legajoId, PersonaPatch patch);
}
