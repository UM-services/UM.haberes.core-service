/**
 * 
 */
package um.haberes.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import um.haberes.core.exception.CargoClaseDetalleException;
import um.haberes.core.kotlin.model.CargoClaseDetalle;
import um.haberes.core.repository.ICargoClaseDetalleRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoClaseDetalleService {

	@Autowired
	private ICargoClaseDetalleRepository repository;

	public List<CargoClaseDetalle> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

	public List<CargoClaseDetalle> findAllByLegajoAndFacultad(Long legajoId, Integer anho, Integer mes, Integer facultadId) {
		return repository.findAllByLegajoIdAndAnhoAndMesAndFacultadId(legajoId, anho, mes, facultadId);
	}

	public List<CargoClaseDetalle> findAllByFacultad(Integer facultadId, Integer anho, Integer mes) {
		return repository.findAllByFacultadIdAndAnhoAndMes(facultadId, anho, mes,
				Sort.by("geograficaId").ascending().and(Sort.by("legajoId").ascending()));
	}

	public List<CargoClaseDetalle> findAllByCargoClaseDetalleIdIn(List<Long> cargoClaseDetalleIds) {
		return repository.findAllByCargoClaseDetalleIdIn(cargoClaseDetalleIds);
	}

	public List<CargoClaseDetalle> findAllByCargoClasePeriodo(Long cargoClasePeriodoId) {
		return repository.findAllByCargoClasePeriodoIdOrderByCargoClaseDetalleId(cargoClasePeriodoId);
	}

	public List<CargoClaseDetalle> findAllByCargoClase(Long cargoClaseId, Integer anho, Integer mes) {
		return repository.findAllByCargoClaseIdAndAnhoAndMesOrderByLegajoId(cargoClaseId, anho, mes);
	}

	public List<CargoClaseDetalle> saveAll(List<CargoClaseDetalle> detalles) {
		repository.saveAll(detalles);
		return detalles;
	}

	public CargoClaseDetalle add(CargoClaseDetalle cargoClaseDetalle) {
		repository.save(cargoClaseDetalle);
		return cargoClaseDetalle;
	}

	public CargoClaseDetalle update(CargoClaseDetalle newCargoClaseDetalle, Long cargoClaseDetalleId) {
		return repository.findByCargoClaseDetalleId(cargoClaseDetalleId).map(cargoClaseDetalle -> {
			cargoClaseDetalle = new CargoClaseDetalle(cargoClaseDetalleId, newCargoClaseDetalle.getLegajoId(),
					newCargoClaseDetalle.getAnho(), newCargoClaseDetalle.getMes(),
					newCargoClaseDetalle.getCargoClaseId(), newCargoClaseDetalle.getDependenciaId(),
					newCargoClaseDetalle.getFacultadId(), newCargoClaseDetalle.getGeograficaId(),
					newCargoClaseDetalle.getHoras(), newCargoClaseDetalle.getValorHora(),
					newCargoClaseDetalle.getCargoClasePeriodoId(), newCargoClaseDetalle.getLiquidado(),
					newCargoClaseDetalle.getPersona(), newCargoClaseDetalle.getCargoClase(),
					newCargoClaseDetalle.getDependencia(), newCargoClaseDetalle.getFacultad(),
					newCargoClaseDetalle.getGeografica(), newCargoClaseDetalle.getCargoClasePeriodo());
			cargoClaseDetalle = repository.save(cargoClaseDetalle);
			return cargoClaseDetalle;
		}).orElseThrow(() -> new CargoClaseDetalleException(cargoClaseDetalleId));
	}

	@Transactional
	public void delete(Long cargoClaseDetalleId) {
		repository.deleteByCargoClaseDetalleId(cargoClaseDetalleId);
	}

	@Transactional
	public void deleteAllByCargoClasePeriodoId(Long cargoClasePeriodoId) {
		repository.deleteAllByCargoClasePeriodoId(cargoClasePeriodoId);
	}

}
