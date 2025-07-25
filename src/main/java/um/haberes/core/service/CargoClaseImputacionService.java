/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.CargoClaseImputacionException;
import um.haberes.core.kotlin.model.CargoClaseImputacion;
import um.haberes.core.repository.CargoClaseImputacionRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoClaseImputacionService {

	@Autowired
	private CargoClaseImputacionRepository repository;

	public List<CargoClaseImputacion> findAll() {
		return repository.findAll();
	}

	public CargoClaseImputacion findByCargoclaseimputacionId(Long cargoClaseImputacionId) {
		return repository.findByCargoClaseImputacionId(cargoClaseImputacionId)
				.orElseThrow(() -> new CargoClaseImputacionException(cargoClaseImputacionId));
	}

	public CargoClaseImputacion findByUnique(Integer dependenciaId, Integer facultadId, Integer geograficaId,
			Long cargoClaseId) {
		return repository
				.findByDependenciaIdAndFacultadIdAndGeograficaIdAndCargoClaseId(dependenciaId, facultadId, geograficaId,
						cargoClaseId)
				.orElseThrow(() -> new CargoClaseImputacionException(dependenciaId, facultadId, geograficaId,
						cargoClaseId));
	}

	public CargoClaseImputacion add(CargoClaseImputacion cargoClaseImputacion) {
		cargoClaseImputacion = repository.save(cargoClaseImputacion);
		return cargoClaseImputacion;
	}

	public CargoClaseImputacion update(CargoClaseImputacion newCargoClaseImputacion, Long cargoClaseImputacionId) {
		return repository.findByCargoClaseImputacionId(cargoClaseImputacionId).map(cargoClaseImputacion -> {
			cargoClaseImputacion = new CargoClaseImputacion(cargoClaseImputacionId,
					newCargoClaseImputacion.getDependenciaId(), newCargoClaseImputacion.getFacultadId(),
					newCargoClaseImputacion.getGeograficaId(), newCargoClaseImputacion.getCargoClaseId(),
					newCargoClaseImputacion.getCuentaSueldos(), newCargoClaseImputacion.getCuentaAportes());
			repository.save(cargoClaseImputacion);
			return cargoClaseImputacion;
		}).orElseThrow(() -> new CargoClaseImputacionException(cargoClaseImputacionId));
	}

}
