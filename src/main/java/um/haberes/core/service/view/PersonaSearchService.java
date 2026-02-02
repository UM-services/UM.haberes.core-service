/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.view.PersonaSearch;
import um.haberes.core.repository.view.PersonaSearchRepository;

/**
 * @author daniel
 *
 */
@Service
@RequiredArgsConstructor
public class PersonaSearchService {

	private final PersonaSearchRepository repository;

	public List<PersonaSearch> findAllByStrings(List<String> conditions) {
		return repository.findAllByStrings(conditions);
	}

}
