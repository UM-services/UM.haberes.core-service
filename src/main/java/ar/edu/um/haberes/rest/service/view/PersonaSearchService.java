/**
 * 
 */
package ar.edu.um.haberes.rest.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.model.view.PersonaSearch;
import ar.edu.um.haberes.rest.repository.view.IPersonaSearchRepository;

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
