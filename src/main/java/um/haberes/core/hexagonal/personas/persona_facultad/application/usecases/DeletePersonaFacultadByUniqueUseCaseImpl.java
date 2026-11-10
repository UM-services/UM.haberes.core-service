package um.haberes.core.hexagonal.personas.persona_facultad.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.in.DeletePersonaFacultadByUniqueUseCase;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.ports.out.PersonaFacultadRepository;

@Component
@RequiredArgsConstructor
public class DeletePersonaFacultadByUniqueUseCaseImpl implements DeletePersonaFacultadByUniqueUseCase {

    private final PersonaFacultadRepository personaFacultadRepository;

    @Transactional
    @Override
    public void deletePersonaFacultadByUnique(Long legajoId, Integer facultadId) {
        personaFacultadRepository.deleteByLegajoIdAndFacultadId(legajoId, facultadId);
    }
}
