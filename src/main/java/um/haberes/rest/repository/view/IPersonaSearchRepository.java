/**
 * 
 */
package um.haberes.rest.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.rest.kotlin.view.PersonaSearch;

/**
 * @author daniel
 *
 */
@Repository
public interface IPersonaSearchRepository extends JpaRepository<PersonaSearch, Long>, IPersonaSearchRepositoryCustom {

}
