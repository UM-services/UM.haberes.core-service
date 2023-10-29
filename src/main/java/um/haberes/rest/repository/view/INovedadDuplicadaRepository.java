/**
 * 
 */
package um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import um.haberes.rest.kotlin.model.view.NovedadDuplicada;

/**
 * @author daniel
 *
 */
public interface INovedadDuplicadaRepository extends JpaRepository<NovedadDuplicada, String> {

	public List<NovedadDuplicada> findAllByAnhoAndMes(Integer anho, Integer mes);

}
