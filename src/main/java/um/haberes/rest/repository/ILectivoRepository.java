/**
 * 
 */
package um.haberes.rest.repository;

import java.util.Optional;

import um.haberes.rest.kotlin.model.Lectivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.rest.kotlin.model.Lectivo;

/**
 * @author daniel
 *
 */
@Repository
public interface ILectivoRepository extends JpaRepository<Lectivo, Integer> {

	public Optional<Lectivo> findByLectivoId(Integer lectivoId);

}
