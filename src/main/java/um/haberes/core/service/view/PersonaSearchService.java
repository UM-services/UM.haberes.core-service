/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import um.haberes.core.model.view.PersonaSearch;
import um.haberes.core.repository.view.JpaPersonaSearchRepository;

/**
 * @author daniel
 *
 */
@Service
@RequiredArgsConstructor
public class PersonaSearchService {

	private final JpaPersonaSearchRepository repository;

	public List<PersonaSearch> findAllByStrings(List<String> conditions) {
		return repository.findAllByStrings(conditions);
	}

}
