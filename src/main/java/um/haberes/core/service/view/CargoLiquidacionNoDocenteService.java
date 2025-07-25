/**
 * 
 */
package um.haberes.core.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.kotlin.model.view.CargoLiquidacionNoDocente;
import um.haberes.core.repository.view.CargoLiquidacionNoDocenteRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoLiquidacionNoDocenteService {

	@Autowired
	private CargoLiquidacionNoDocenteRepository repository;

	public List<CargoLiquidacionNoDocente> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

}
