/**
 * 
 */
package um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.view.LegajoCursoCantidad;

/**
 * @author daniel
 *
 */
@Repository
public interface ILegajoCursoCantidadRepository extends JpaRepository<LegajoCursoCantidad, String> {

	public List<LegajoCursoCantidad> findAllByAnhoAndMes(Integer anho, Integer mes);

}
