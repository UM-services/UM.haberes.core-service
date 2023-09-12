/**
 * 
 */
package um.haberes.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.rest.kotlin.model.Acreditacion;

/**
 * @author daniel
 *
 */
public interface IAcreditacionRepository extends JpaRepository<Acreditacion, Long>{

	public Optional<Acreditacion> findByAcreditacionId(Long acreditacionId);

	public Optional<Acreditacion> findByAnhoAndMes(Integer anho, Integer mes);

}
