/**
 * 
 */
package um.haberes.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.ControlEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaControlRepository extends JpaRepository<ControlEntity, Long> {

	public Optional<ControlEntity> findByAnhoAndMes(Integer anho, Integer mes);

	public Optional<ControlEntity> findByControlId(Long controlId);

}
