/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.view.NovedadExiste;

/**
 * @author daniel
 *
 */
@Repository
public interface INovedadExisteRepository extends JpaRepository<NovedadExiste, String> {

	public List<NovedadExiste> findAllByAnhoAndMes(Integer anho, Integer mes);

}
