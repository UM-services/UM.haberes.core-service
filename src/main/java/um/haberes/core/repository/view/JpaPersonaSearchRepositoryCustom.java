/**
 * 
 */
package um.haberes.core.repository.view;

import um.haberes.core.model.view.PersonaSearch;

import java.util.List;

/**
 * @author daniel
 *
 */
public interface JpaPersonaSearchRepositoryCustom {

	List<PersonaSearch> findAllByStrings(List<String> conditions);

}
