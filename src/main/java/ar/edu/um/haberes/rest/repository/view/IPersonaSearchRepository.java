/**
 * 
 */
package ar.edu.um.haberes.rest.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.view.PersonaSearch;

/**
 * @author daniel
 *
 */
@Repository
public interface IPersonaSearchRepository extends JpaRepository<PersonaSearch, Long>, IPersonaSearchRepositoryCustom {

}
