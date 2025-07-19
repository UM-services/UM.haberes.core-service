/**
 * 
 */
package um.haberes.core.repository.view;

import um.haberes.core.kotlin.model.view.PersonaSearch;

import java.util.List;

/**
 * @author daniel
 *
 */
public interface PersonaSearchRepositoryCustom {

	public List<PersonaSearch> findAllByStrings(List<String> conditions);

}
