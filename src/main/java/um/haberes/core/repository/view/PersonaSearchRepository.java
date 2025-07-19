/**
 * 
 */
package um.haberes.core.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.view.PersonaSearch;

/**
 * @author daniel
 *
 */
@Repository
public interface PersonaSearchRepository extends JpaRepository<PersonaSearch, Long>, PersonaSearchRepositoryCustom {

}
