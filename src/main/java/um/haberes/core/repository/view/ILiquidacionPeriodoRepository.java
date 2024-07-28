/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.view.LiquidacionPeriodo;

/**
 * @author daniel
 *
 */
@Repository
public interface ILiquidacionPeriodoRepository extends JpaRepository<LiquidacionPeriodo, Long> {

	public List<LiquidacionPeriodo> findAllByLegajoIdAndPeriodoGreaterThanEqual(Long legajoId, Long periodo, Sort sort);

}
