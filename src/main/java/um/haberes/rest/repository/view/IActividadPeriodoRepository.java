/**
 * 
 */
package um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import um.haberes.rest.model.view.ActividadPeriodo;

/**
 * @author daniel
 *
 */
@Repository
public interface IActividadPeriodoRepository extends JpaRepository<ActividadPeriodo, Long> {

	public List<ActividadPeriodo> findAllByLegajoIdAndPeriodoGreaterThanEqual(Long legajoId, Long periodo, Sort sort);

}
