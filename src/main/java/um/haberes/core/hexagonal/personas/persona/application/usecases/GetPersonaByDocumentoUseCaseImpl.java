package um.haberes.core.hexagonal.personas.persona.application.usecases;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.GetPersonaByDocumentoUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;

@Component
@RequiredArgsConstructor
public class GetPersonaByDocumentoUseCaseImpl implements GetPersonaByDocumentoUseCase {

    private final PersonaRepository personaRepository;

    @Override
    public Optional<Persona> getPersonaByDocumento(BigDecimal documento) {
        return personaRepository.findByDocumento(documento);
    }
}
