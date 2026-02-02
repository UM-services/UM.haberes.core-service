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

	List<PersonaSearch> findAllByStrings(List<String> conditions);

}
