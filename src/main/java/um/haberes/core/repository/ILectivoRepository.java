/**
 * 
 */
package um.haberes.core.repository;

import java.util.Optional;

import um.haberes.core.kotlin.model.Lectivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface ILectivoRepository extends JpaRepository<Lectivo, Integer> {

	public Optional<Lectivo> findByLectivoId(Integer lectivoId);

}
