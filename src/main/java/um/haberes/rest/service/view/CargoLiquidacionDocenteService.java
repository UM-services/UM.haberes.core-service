/**
 * 
 */
package um.haberes.rest.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.model.view.CargoLiquidacionDocente;
import um.haberes.rest.repository.view.ICargoLiquidacionDocenteRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoLiquidacionDocenteService {

	@Autowired
	private ICargoLiquidacionDocenteRepository repository;

	public List<CargoLiquidacionDocente> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

}
