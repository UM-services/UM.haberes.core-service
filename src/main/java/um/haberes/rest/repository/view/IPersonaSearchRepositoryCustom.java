/**
 * 
 */
package um.haberes.rest.repository.view;

import java.util.List;

import um.haberes.rest.model.view.PersonaSearch;

/**
 * @author daniel
 *
 */
public interface IPersonaSearchRepositoryCustom {

	public List<PersonaSearch> findAllByStrings(List<String> conditions);

}
