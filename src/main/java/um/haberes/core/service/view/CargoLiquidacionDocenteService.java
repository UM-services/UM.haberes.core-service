/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.view.CargoLiquidacionDocente;
import um.haberes.core.repository.view.CargoLiquidacionDocenteRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoLiquidacionDocenteService {

	@Autowired
	private CargoLiquidacionDocenteRepository repository;

	public List<CargoLiquidacionDocente> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

}
