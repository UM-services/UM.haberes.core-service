/**
 * 
 */
package ar.edu.um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.view.CargoClaseDetallePeriodo;

/**
 * @author daniel
 *
 */
@Repository
public interface ICargoClaseDetallePeriodoRepository extends JpaRepository<CargoClaseDetallePeriodo, Long> {

	public List<CargoClaseDetallePeriodo> findAllByLiquidadoAndPeriodoGreaterThanEqual(Byte liquidado, Long periodo);

}
