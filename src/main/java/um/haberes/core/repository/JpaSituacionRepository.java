/**
 * 
 */
package um.haberes.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.model.SituacionEntity;

/**
 * @author daniel
 *
 */
public interface JpaSituacionRepository extends JpaRepository<SituacionEntity, Integer> {

}
