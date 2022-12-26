/**
 * 
 */
package ar.edu.um.haberes.rest.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.um.haberes.rest.model.view.CargoLiquidacionPeriodo;
import ar.edu.um.haberes.rest.repository.view.ICargoLiquidacionPeriodoRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoLiquidacionPeriodoService {

	@Autowired
	private ICargoLiquidacionPeriodoRepository repository;

	public List<CargoLiquidacionPeriodo> findAllRestoByLegajo(Long legajoId, Integer anho, Integer mes, Integer categoriaId) {
		return repository.findAllByLegajoIdAndCategoriaIdAndPeriodoGreaterThanEqual(legajoId, categoriaId,
				anho * 100L + mes);
	}

}
