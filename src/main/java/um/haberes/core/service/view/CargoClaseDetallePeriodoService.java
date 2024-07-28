/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.view.CargoClaseDetallePeriodo;
import um.haberes.core.repository.view.ICargoClaseDetallePeriodoRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoClaseDetallePeriodoService {

	@Autowired
	private ICargoClaseDetallePeriodoRepository repository;

	public List<CargoClaseDetallePeriodo> findAllLiquidadoByPeriodoResto(Integer anho, Integer mes) {
		return repository.findAllByLiquidadoAndPeriodoGreaterThanEqual((byte) 0, anho * 100L + mes);
	}

}
