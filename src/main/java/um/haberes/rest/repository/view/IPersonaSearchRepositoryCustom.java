/**
 * 
 */
package um.haberes.rest.repository.view;

import um.haberes.rest.kotlin.view.PersonaSearch;

import java.util.List;

/**
 * @author daniel
 *
 */
public interface IPersonaSearchRepositoryCustom {

	public List<PersonaSearch> findAllByStrings(List<String> conditions);

}
