package um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.personas.persona_facultad.domain.model.PersonaFacultad;

public interface GetPersonaFacultadesByLegajoUseCase {

    List<PersonaFacultad> getPersonaFacultadesByLegajo(Long legajoId);
}
