/**
 * 
 */
package um.haberes.core.repository;

import java.util.Optional;

import um.haberes.core.model.LectivoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaLectivoRepository extends JpaRepository<LectivoEntity, Integer> {

	public Optional<LectivoEntity> findByLectivoId(Integer lectivoId);

}
