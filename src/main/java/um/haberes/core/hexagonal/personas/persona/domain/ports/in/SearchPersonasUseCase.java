package um.haberes.core.hexagonal.personas.persona.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.personas.persona.domain.model.PersonaSearch;

public interface SearchPersonasUseCase {

    List<PersonaSearch> searchPersonas(List<String> conditions);
}
