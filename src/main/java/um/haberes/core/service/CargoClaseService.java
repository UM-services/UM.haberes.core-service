/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.CargoClaseException;
import um.haberes.core.kotlin.model.CargoClase;
import um.haberes.core.repository.ICargoClaseRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoClaseService {

	@Autowired
	private ICargoClaseRepository repository;

	public List<CargoClase> findAll() {
		return repository.findAll();
	}

	public CargoClase findByCargoClaseId(Long cargoClaseId) {
		return repository.findByCargoClaseId(cargoClaseId)
				.orElseThrow(() -> new CargoClaseException(cargoClaseId));
	}

	public CargoClase add(CargoClase cargoClase) {
		repository.save(cargoClase);
		return cargoClase;
	}

	public CargoClase update(CargoClase newCargoClase, Long cargoClaseId) {
		return repository.findByCargoClaseId(cargoClaseId).map(cargoClase -> {
			cargoClase = new CargoClase(cargoClaseId, newCargoClase.getNombre(), newCargoClase.getClaseId(),
					newCargoClase.getClase());
			repository.save(cargoClase);
			return cargoClase;
		}).orElseThrow(() -> new CargoClaseException(cargoClaseId));
	}

}
