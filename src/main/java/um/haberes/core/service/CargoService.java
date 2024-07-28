/**
 * 
 */
package um.haberes.core.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.haberes.core.exception.CargoLiquidacionException;
import um.haberes.core.kotlin.model.Cargo;
import um.haberes.core.repository.ICargoRepository;
import um.haberes.core.util.Periodo;

/**
 * @author daniel
 *
 */
@Service
public class CargoService {

	@Autowired
	private ICargoRepository repository;

	public List<Cargo> findAllByLegajoId(Long legajoId) {
		return repository.findAllByLegajoIdOrderByCargoIdDesc(legajoId);
	}

	public List<Cargo> findAllNoDocenteByPeriodo(Long legajoId, Integer anho, Integer mes) {
		// Buscar todos los cargos con inicio sin fin
		// Buscar todos los cargos entre inicio y fin de mes
		List<Cargo> cargos = Stream.concat(
				repository.findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaIsNull(legajoId,
						Periodo.firstDay(anho, mes)).stream(),
				repository.findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaGreaterThanEqual(legajoId,
						Periodo.firstDay(anho, mes), Periodo.lastDay(anho, mes)).stream())
				.collect(Collectors.toList());
		return cargos.stream().filter(c -> c.getCategoria().getNoDocente() == 1).collect(Collectors.toList());
	}

	public List<Cargo> findAllDocenteByPeriodo(Long legajoId, Integer anho, Integer mes) {
		// Buscar todos los cargos con inicio sin fin
		// Buscar todos los cargos entre inicio y fin de mes
		List<Cargo> cargos = Stream.concat(
				repository.findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaIsNull(legajoId,
						Periodo.firstDay(anho, mes)).stream(),
				repository.findAllByLegajoIdAndFechaAltaLessThanEqualAndFechaBajaGreaterThanEqual(legajoId,
						Periodo.firstDay(anho, mes), Periodo.lastDay(anho, mes)).stream())
				.collect(Collectors.toList());
		return cargos.stream().filter(c -> c.getCategoria().getDocente() == 1).collect(Collectors.toList());
	}

	public Cargo findByCargoId(Long cargoId) {
		return repository.findByCargoId(cargoId).orElseThrow(() -> new CargoLiquidacionException(cargoId));
	}

	public Cargo add(Cargo cargo) {
		cargo = repository.save(cargo);
		return cargo;
	}

	public Cargo update(Cargo newCargo, Long cargoId) {
		return repository.findByCargoId(cargoId).map(cargo -> {
			cargo = new Cargo(cargoId, newCargo.getLegajoId(), newCargo.getFechaAlta(), newCargo.getFechaBaja(),
					newCargo.getDependenciaId(), newCargo.getCategoriaId(), newCargo.getJornada(),
					newCargo.getPresentismo(), newCargo.getHorasJornada(), newCargo.getPersona(),
					newCargo.getDependencia(), newCargo.getCategoria());
			cargo = repository.save(cargo);
			return cargo;
		}).orElseThrow(() -> new CargoLiquidacionException(cargoId));
	}

	@Transactional
	public void delete(Long cargoId) {
		repository.deleteByCargoId(cargoId);
	}

}
