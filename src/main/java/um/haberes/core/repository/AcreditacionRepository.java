/**
 * 
 */
package um.haberes.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.kotlin.model.Acreditacion;

/**
 * @author daniel
 *
 */
public interface AcreditacionRepository extends JpaRepository<Acreditacion, Long>{

	public Optional<Acreditacion> findByAcreditacionId(Long acreditacionId);

	public Optional<Acreditacion> findByAnhoAndMes(Integer anho, Integer mes);

}
