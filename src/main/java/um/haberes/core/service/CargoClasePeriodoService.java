/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.view.CargoClasePeriodoException;
import um.haberes.core.model.CargoClasePeriodoEntity;
import um.haberes.core.repository.JpaCargoClasePeriodoRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoClasePeriodoService {

	@Autowired
	private JpaCargoClasePeriodoRepository repository;

	public List<CargoClasePeriodoEntity> findAllByFacultad(Integer facultadId) {
		return repository.findAllByFacultadIdOrderByLegajoId(facultadId);
	}

	public List<CargoClasePeriodoEntity> findAllByLegajo(Long legajoId) {
		return repository.findAllByLegajoIdOrderByCargoClasePeriodoIdDesc(legajoId);
	}

	public CargoClasePeriodoEntity findByCargoClasePeriodoId(Long cargoClasePeriodoId) {
		return repository.findByCargoClasePeriodoId(cargoClasePeriodoId)
				.orElseThrow(() -> new CargoClasePeriodoException(cargoClasePeriodoId));
	}

	public CargoClasePeriodoEntity add(CargoClasePeriodoEntity cargoClasePeriodo) {
		repository.save(cargoClasePeriodo);
		return cargoClasePeriodo;
	}

	public CargoClasePeriodoEntity update(CargoClasePeriodoEntity newCargoClasePeriodo, Long cargoClasePeriodoId) {
		return repository.findByCargoClasePeriodoId(cargoClasePeriodoId).map(cargoClasePeriodo -> {
			cargoClasePeriodo = new CargoClasePeriodoEntity(
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
		}).orElseThrow(() -> new CargoClasePeriodoException(cargoClasePeriodoId));
	}

	public void delete(Long cargoClasePeriodoId) {
		repository.deleteByCargoClasePeriodoId(cargoClasePeriodoId);
	}

}
