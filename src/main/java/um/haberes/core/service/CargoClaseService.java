/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.CargoClaseException;
import um.haberes.core.model.CargoClaseEntity;
import um.haberes.core.repository.JpaCargoClaseRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoClaseService {

	@Autowired
	private JpaCargoClaseRepository repository;

	public List<CargoClaseEntity> findAll() {
		return repository.findAll();
	}

	public CargoClaseEntity findByCargoClaseId(Long cargoClaseId) {
		return repository.findByCargoClaseId(cargoClaseId)
				.orElseThrow(() -> new CargoClaseException(cargoClaseId));
	}

	public CargoClaseEntity add(CargoClaseEntity cargoClase) {
		repository.save(cargoClase);
		return cargoClase;
	}

	public CargoClaseEntity update(CargoClaseEntity newCargoClase, Long cargoClaseId) {
		return repository.findByCargoClaseId(cargoClaseId).map(cargoClase -> {
			cargoClase = new CargoClaseEntity(cargoClaseId, newCargoClase.getNombre(), newCargoClase.getClaseId(),
					newCargoClase.getClase());
			repository.save(cargoClase);
			return cargoClase;
		}).orElseThrow(() -> new CargoClaseException(cargoClaseId));
	}

}
