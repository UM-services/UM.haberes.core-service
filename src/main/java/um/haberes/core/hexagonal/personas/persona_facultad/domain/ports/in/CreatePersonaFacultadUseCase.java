package um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.in;

import um.haberes.core.hexagonal.personas.persona_facultad.domain.model.PersonaFacultad;

public interface CreatePersonaFacultadUseCase {

    PersonaFacultad createPersonaFacultad(PersonaFacultad personaFacultad);
}
