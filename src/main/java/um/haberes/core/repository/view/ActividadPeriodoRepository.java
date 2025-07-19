/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.view.ActividadPeriodo;

/**
 * @author daniel
 *
 */
@Repository
public interface ActividadPeriodoRepository extends JpaRepository<ActividadPeriodo, Long> {

	public List<ActividadPeriodo> findAllByLegajoIdAndPeriodoGreaterThanEqual(Long legajoId, Long periodo, Sort sort);

}
