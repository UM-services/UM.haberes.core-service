/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.core.model.view.NovedadDuplicada;

/**
 * @author daniel
 *
 */
public interface JpaNovedadDuplicadaRepository extends JpaRepository<NovedadDuplicada, String> {

	public List<NovedadDuplicada> findAllByAnhoAndMes(Integer anho, Integer mes);

}
