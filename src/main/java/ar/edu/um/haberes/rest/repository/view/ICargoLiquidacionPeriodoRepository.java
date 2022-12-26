/**
 * 
 */
package ar.edu.um.haberes.rest.repository.view;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.haberes.rest.model.view.CargoLiquidacionPeriodo;

/**
 * @author daniel
 *
 */
@Repository
public interface ICargoLiquidacionPeriodoRepository extends JpaRepository<CargoLiquidacionPeriodo, Long> {

	public List<CargoLiquidacionPeriodo> findAllByLegajoIdAndCategoriaIdAndPeriodoGreaterThanEqual(Long legajoId,
			Integer categoriaId, Long periodo);

}
