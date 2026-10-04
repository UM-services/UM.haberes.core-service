package um.haberes.core.hexagonal.personas.persona.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.personas.persona.domain.model.PersonaSearch;

public interface PersonaSearchRepository {

    List<PersonaSearch> findMatching(List<String> conditions);
}
