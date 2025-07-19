/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.kotlin.model.view.ImputadoTotal;

/**
 * @author daniel
 *
 */
public interface ImputadoTotalRepository extends JpaRepository<ImputadoTotal, String> {
	
	public Optional<ImputadoTotal> findByAnhoAndMes(Integer anho, Integer mes);

}
