package um.haberes.core.hexagonal.personas.persona.domain.ports.in;

import java.math.BigDecimal;
import java.util.Optional;

import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;

public interface GetPersonaByDocumentoUseCase {

    Optional<Persona> getPersonaByDocumento(BigDecimal documento);
}
