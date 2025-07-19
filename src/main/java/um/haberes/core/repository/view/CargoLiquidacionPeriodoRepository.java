/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.view.CargoLiquidacionPeriodo;

/**
 * @author daniel
 *
 */
@Repository
public interface CargoLiquidacionPeriodoRepository extends JpaRepository<CargoLiquidacionPeriodo, Long> {

	public List<CargoLiquidacionPeriodo> findAllByLegajoIdAndCategoriaIdAndPeriodoGreaterThanEqual(Long legajoId,
																								   Integer categoriaId, Long periodo);

}
