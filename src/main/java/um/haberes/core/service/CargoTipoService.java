/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.CargoTipoException;
import um.haberes.core.kotlin.model.CargoTipo;
import um.haberes.core.repository.CargoTipoRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoTipoService {

	@Autowired
	private CargoTipoRepository repository;

	public List<CargoTipo> findAll() {
		return repository.findAll();
	}

	public List<CargoTipo> findAllByCargoTipoIdIn(List<Integer> cargoTipoIds) {
		return repository.findAllByCargoTipoIdIn(cargoTipoIds);
	}

	public CargoTipo findByCargoTipoId(Integer cargoTipoId) {
		return repository.findByCargoTipoId(cargoTipoId).orElseThrow(() -> new CargoTipoException(cargoTipoId));
	}

}
