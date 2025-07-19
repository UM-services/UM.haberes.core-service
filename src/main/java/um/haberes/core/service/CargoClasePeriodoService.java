/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.view.CargoClasePeriodoNotFoundException;
import um.haberes.core.kotlin.model.CargoClasePeriodo;
import um.haberes.core.repository.CargoClasePeriodoRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoClasePeriodoService {

	@Autowired
	private CargoClasePeriodoRepository repository;

	public List<CargoClasePeriodo> findAllByFacultad(Integer facultadId) {
		return repository.findAllByFacultadIdOrderByLegajoId(facultadId);
	}

	public List<CargoClasePeriodo> findAllByLegajo(Long legajoId) {
		return repository.findAllByLegajoIdOrderByCargoClasePeriodoIdDesc(legajoId);
	}

	public CargoClasePeriodo findByCargoClasePeriodoId(Long cargoClasePeriodoId) {
		return repository.findByCargoClasePeriodoId(cargoClasePeriodoId)
				.orElseThrow(() -> new CargoClasePeriodoNotFoundException(cargoClasePeriodoId));
	}

	public CargoClasePeriodo add(CargoClasePeriodo cargoClasePeriodo) {
		repository.save(cargoClasePeriodo);
		return cargoClasePeriodo;
	}

	public CargoClasePeriodo update(CargoClasePeriodo newCargoClasePeriodo, Long cargoClasePeriodoId) {
		return repository.findByCargoClasePeriodoId(cargoClasePeriodoId).map(cargoClasePeriodo -> {
			cargoClasePeriodo = new CargoClasePeriodo(
					cargoClasePeriodoId,
					newCargoClasePeriodo.getLegajoId(),
					newCargoClasePeriodo.getCargoClaseId(),
					newCargoClasePeriodo.getDependenciaId(),
					newCargoClasePeriodo.getFacultadId(),
					newCargoClasePeriodo.getGeograficaId(),
					newCargoClasePeriodo.getPeriodoDesde(),
					newCargoClasePeriodo.getPeriodoHasta(),
					newCargoClasePeriodo.getHoras(),
					newCargoClasePeriodo.getValorHora(),
					newCargoClasePeriodo.getAplicaAdicional(),
					newCargoClasePeriodo.getDescripcion(),
					newCargoClasePeriodo.getPersona(),
					newCargoClasePeriodo.getCargoClase(),
					newCargoClasePeriodo.getDependencia(),
					newCargoClasePeriodo.getFacultad(),
					newCargoClasePeriodo.getGeografica()
			);
			repository.save(cargoClasePeriodo);
			return cargoClasePeriodo;
		}).orElseThrow(() -> new CargoClasePeriodoNotFoundException(cargoClasePeriodoId));
	}

	public void delete(Long cargoClasePeriodoId) {
		repository.deleteByCargoClasePeriodoId(cargoClasePeriodoId);
	}

}
