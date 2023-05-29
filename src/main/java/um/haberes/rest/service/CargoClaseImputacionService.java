/**
 * 
 */
package um.haberes.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.CargoClaseImputacionNotFoundException;
import um.haberes.rest.model.CargoClaseImputacion;
import um.haberes.rest.repository.ICargoClaseImputacionRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoClaseImputacionService {

	@Autowired
	private ICargoClaseImputacionRepository repository;

	public List<CargoClaseImputacion> findAll() {
		return repository.findAll();
	}

	public CargoClaseImputacion findByCargoclaseimputacionId(Long cargoClaseImputacionId) {
		return repository.findByCargoClaseImputacionId(cargoClaseImputacionId)
				.orElseThrow(() -> new CargoClaseImputacionNotFoundException(cargoClaseImputacionId));
	}

	public CargoClaseImputacion findByUnique(Integer dependenciaId, Integer facultadId, Integer geograficaId,
			Long cargoClaseId) {
		return repository
				.findByDependenciaIdAndFacultadIdAndGeograficaIdAndCargoClaseId(dependenciaId, facultadId, geograficaId,
						cargoClaseId)
				.orElseThrow(() -> new CargoClaseImputacionNotFoundException(dependenciaId, facultadId, geograficaId,
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
		}).orElseThrow(() -> new CargoClaseImputacionNotFoundException(cargoClaseImputacionId));
	}

}
