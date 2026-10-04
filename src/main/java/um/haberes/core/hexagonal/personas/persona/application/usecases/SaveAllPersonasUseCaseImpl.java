package um.haberes.core.hexagonal.personas.persona.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.SaveAllPersonasUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;

@Component
@RequiredArgsConstructor
public class SaveAllPersonasUseCaseImpl implements SaveAllPersonasUseCase {

    private final PersonaRepository personaRepository;

    @Override
    public List<Persona> saveAllPersonas(List<Persona> personas) {
        return personaRepository.saveAll(personas);
    }
}
