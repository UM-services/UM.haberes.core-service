/**
 * 
 */
package ar.edu.um.haberes.rest.repository.view;

import java.util.List;

import ar.edu.um.haberes.rest.model.view.PersonaSearch;

/**
 * @author daniel
 *
 */
public interface IPersonaSearchRepositoryCustom {

	public List<PersonaSearch> findAllByStrings(List<String> conditions);

}
