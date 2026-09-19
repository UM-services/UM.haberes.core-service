package um.haberes.core.hexagonal.personas.persona.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;

public interface GetPersonaByLegajoUseCase {

    Optional<Persona> getPersonaByLegajo(Long legajoId);
}
