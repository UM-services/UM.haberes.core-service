/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.view.PersonaSearch;
import um.haberes.core.repository.view.IPersonaSearchRepository;

/**
 * @author daniel
 *
 */
@Service
public class PersonaSearchService {

	@Autowired
	private IPersonaSearchRepository repository;

	public List<PersonaSearch> findAllByStrings(List<String> conditions) {
		return repository.findAllByStrings(conditions);
	}

}
