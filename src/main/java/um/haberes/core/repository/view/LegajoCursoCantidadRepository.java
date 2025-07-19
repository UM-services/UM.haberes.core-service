/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.view.LegajoCursoCantidad;

/**
 * @author daniel
 *
 */
@Repository
public interface LegajoCursoCantidadRepository extends JpaRepository<LegajoCursoCantidad, String> {

	public List<LegajoCursoCantidad> findAllByAnhoAndMes(Integer anho, Integer mes);

}
