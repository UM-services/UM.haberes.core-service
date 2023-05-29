/**
 * 
 */
package um.haberes.rest.service.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.model.view.CargoLiquidacionNoDocente;
import um.haberes.rest.repository.view.ICargoLiquidacionNoDocenteRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoLiquidacionNoDocenteService {

	@Autowired
	private ICargoLiquidacionNoDocenteRepository repository;

	public List<CargoLiquidacionNoDocente> findAllByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMes(anho, mes);
	}

}
