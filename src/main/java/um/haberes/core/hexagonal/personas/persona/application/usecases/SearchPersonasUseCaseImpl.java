package um.haberes.core.hexagonal.personas.persona.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona.domain.model.PersonaSearch;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.SearchPersonasUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaSearchRepository;

@Component
@RequiredArgsConstructor
public class SearchPersonasUseCaseImpl implements SearchPersonasUseCase {

    private final PersonaSearchRepository personaSearchRepository;

    @Override
    public List<PersonaSearch> searchPersonas(List<String> conditions) {
        return personaSearchRepository.findMatching(conditions);
    }
}
