/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.view.CodigoPeriodo;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaCodigoPeriodoRepository extends JpaRepository<CodigoPeriodo, String> {

	public List<CodigoPeriodo> findAllByAnhoAndMes(Integer anho, Integer mes);

}
