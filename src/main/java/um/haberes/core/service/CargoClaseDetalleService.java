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
import um.haberes.core.model.CargoClaseDetalleEntity;
import um.haberes.core.repository.JpaCargoClaseDetalleRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoClaseDetalleService {

	@Autowired
	private JpaCargoClaseDetalleRepository repository;

	public List<CargoClaseDetalleEntity> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

	public List<CargoClaseDetalleEntity> findAllByLegajoAndFacultad(Long legajoId, Integer anho, Integer mes, Integer facultadId) {
		return repository.findAllByLegajoIdAndAnhoAndMesAndFacultadId(legajoId, anho, mes, facultadId);
	}

	public List<CargoClaseDetalleEntity> findAllByFacultad(Integer facultadId, Integer anho, Integer mes) {
		return repository.findAllByFacultadIdAndAnhoAndMes(facultadId, anho, mes,
				Sort.by("geograficaId").ascending().and(Sort.by("legajoId").ascending()));
	}

	public List<CargoClaseDetalleEntity> findAllByCargoClaseDetalleIdIn(List<Long> cargoClaseDetalleIds) {
		return repository.findAllByCargoClaseDetalleIdIn(cargoClaseDetalleIds);
	}

	public List<CargoClaseDetalleEntity> findAllByCargoClasePeriodo(Long cargoClasePeriodoId) {
		return repository.findAllByCargoClasePeriodoIdOrderByCargoClaseDetalleId(cargoClasePeriodoId);
	}

	public List<CargoClaseDetalleEntity> findAllByCargoClase(Long cargoClaseId, Integer anho, Integer mes) {
		return repository.findAllByCargoClaseIdAndAnhoAndMesOrderByLegajoId(cargoClaseId, anho, mes);
	}

	public List<CargoClaseDetalleEntity> saveAll(List<CargoClaseDetalleEntity> detalles) {
		repository.saveAll(detalles);
		return detalles;
	}

	public CargoClaseDetalleEntity add(CargoClaseDetalleEntity cargoClaseDetalle) {
		repository.save(cargoClaseDetalle);
		return cargoClaseDetalle;
	}

	public CargoClaseDetalleEntity update(CargoClaseDetalleEntity newCargoClaseDetalle, Long cargoClaseDetalleId) {
		return repository.findByCargoClaseDetalleId(cargoClaseDetalleId).map(cargoClaseDetalle -> {
			cargoClaseDetalle = new CargoClaseDetalleEntity(
					cargoClaseDetalleId,
					newCargoClaseDetalle.getLegajoId(),
					newCargoClaseDetalle.getAnho(),
					newCargoClaseDetalle.getMes(),
					newCargoClaseDetalle.getCargoClaseId(),
					newCargoClaseDetalle.getDependenciaId(),
					newCargoClaseDetalle.getFacultadId(),
					newCargoClaseDetalle.getGeograficaId(),
					newCargoClaseDetalle.getHoras(),
					newCargoClaseDetalle.getValorHora(),
					newCargoClaseDetalle.getAplicaAdicional(),
					newCargoClaseDetalle.getCargoClasePeriodoId(),
					newCargoClaseDetalle.getLiquidado(),
					newCargoClaseDetalle.getPersona(),
					newCargoClaseDetalle.getCargoClase(),
					newCargoClaseDetalle.getDependencia(),
					newCargoClaseDetalle.getFacultad(),
					newCargoClaseDetalle.getGeografica(),
					newCargoClaseDetalle.getCargoClasePeriodo()
			);
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
