/**
 * 
 */
package um.haberes.rest.repository.view;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.rest.kotlin.model.view.ImputadoTotal;

/**
 * @author daniel
 *
 */
public interface IImputadoTotalRepository extends JpaRepository<ImputadoTotal, String> {
	
	public Optional<ImputadoTotal> findByAnhoAndMes(Integer anho, Integer mes);

}
