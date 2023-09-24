/**
 * 
 */
package um.haberes.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.rest.kotlin.model.Control;

/**
 * @author daniel
 *
 */
@Repository
public interface IControlRepository extends JpaRepository<Control, Long> {

	public Optional<Control> findByAnhoAndMes(Integer anho, Integer mes);

	public Optional<Control> findByControlId(Long controlId);

}
