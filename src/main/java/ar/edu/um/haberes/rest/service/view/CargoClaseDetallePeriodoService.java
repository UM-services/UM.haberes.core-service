/**
 * 
 */
package ar.edu.um.haberes.rest.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.model.view.CargoClaseDetallePeriodo;
import ar.edu.um.haberes.rest.repository.view.ICargoClaseDetallePeriodoRepository;

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
