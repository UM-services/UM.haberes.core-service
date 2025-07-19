/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.view.CodigoPeriodo;

/**
 * @author daniel
 *
 */
@Repository
public interface CodigoPeriodoRepository extends JpaRepository<CodigoPeriodo, String> {

	public List<CodigoPeriodo> findAllByAnhoAndMes(Integer anho, Integer mes);

}
