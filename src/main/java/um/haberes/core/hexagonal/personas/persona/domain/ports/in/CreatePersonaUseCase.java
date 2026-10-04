package um.haberes.core.hexagonal.personas.persona.domain.ports.in;

import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;

public interface CreatePersonaUseCase {

    Persona createPersona(Persona persona);
}
