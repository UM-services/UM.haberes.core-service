/**
 * 
 */
package um.haberes.core.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.view.CargoClaseDetallePeriodo;

/**
 * @author daniel
 *
 */
@Repository
public interface CargoClaseDetallePeriodoRepository extends JpaRepository<CargoClaseDetallePeriodo, Long> {

	public List<CargoClaseDetallePeriodo> findAllByLiquidadoAndPeriodoGreaterThanEqual(Byte liquidado, Long periodo);

}
