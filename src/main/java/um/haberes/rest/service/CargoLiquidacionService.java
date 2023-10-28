/**
 * 
 */
package um.haberes.rest.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import um.haberes.rest.exception.CargoLiquidacionNotFoundException;
import um.haberes.rest.kotlin.model.CargoLiquidacion;
import um.haberes.rest.kotlin.model.CargoLiquidacionVersion;
import um.haberes.rest.repository.ICargoLiquidacionRepository;

/**
 * @author daniel
 *
 */
@Service
public class CargoLiquidacionService {

	@Autowired
	private ICargoLiquidacionRepository repository;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private CargoLiquidacionVersionService cargoLiquidacionVersionService;

	public List<CargoLiquidacion> findAllByLegajo(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
	}

	public List<CargoLiquidacion> findAllDocenteByLegajo(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes).stream()
				.filter(c -> c.getCategoria().getDocente() == 1).collect(Collectors.toList());
	}

	public List<CargoLiquidacion> findAllNoDocenteByLegajo(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes).stream()
				.filter(c -> c.getCategoria().getNoDocente() == 1).collect(Collectors.toList());
	}

	public List<CargoLiquidacion> findAllAdicionalHCSByLegajo(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMesAndCategoriaIdBetween(legajoId, anho, mes, 801, 811);
	}

	public List<CargoLiquidacion> findAllNoDocenteHistByLegajo(Long legajoId) {
		return repository
				.findAllByLegajoIdAndCategoriaIdInAndCategoriaBasicoGreaterThan(
						legajoId, categoriaService.findAllNoDocentes().stream()
								.map(categoria -> categoria.getCategoriaId()).collect(Collectors.toList()),
						BigDecimal.ZERO);
	}

	public List<CargoLiquidacion> findAllActivosByPeriodo(Integer anho, Integer mes) {
		return repository.findAllByAnhoAndMesAndSituacion(anho, mes, "A", Sort.by("legajoId").ascending()
				.and(Sort.by("dependenciaId").ascending().and(Sort.by("categoriaId").ascending())));
	}

	public List<CargoLiquidacion> findAllActivosByLegajo(Long legajoId, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdAndAnhoAndMesAndSituacion(legajoId, anho, mes, "A");
	}

	public List<CargoLiquidacion> findAllByLegajoIdInAndCategoriaIdInAndAnhoAndMes(List<Long> legajos,
			List<Integer> categorias, Integer anho, Integer mes) {
		return repository.findAllByLegajoIdInAndCategoriaIdInAndAnhoAndMes(legajos, categorias, anho, mes);
	}

	public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdIn(Long legajoId, Integer anho,
			Integer mes, List<Integer> categorias) {
		return repository.findAllByLegajoIdAndAnhoAndMesAndCategoriaIdIn(legajoId, anho, mes, categorias);
	}

	public List<CargoLiquidacion> findAllByLegajoIdAndAnhoAndMesAndCategoriaIdNotIn(Long legajoId, Integer anho,
			Integer mes, List<Integer> categoriaIds) {
		return repository.findAllByLegajoIdAndAnhoAndMesAndCategoriaIdNotIn(legajoId, anho, mes, categoriaIds);
	}

	public CargoLiquidacion findByCargoId(Long cargoLiquidacionId) {
		return repository.findByCargoLiquidacionId(cargoLiquidacionId)
				.orElseThrow(() -> new CargoLiquidacionNotFoundException(cargoLiquidacionId));
	}

	public CargoLiquidacion findByCategoriaNoDocente(Long legajoId, Integer anho, Integer mes, Integer categoriaId) {
		return repository.findByLegajoIdAndAnhoAndMesAndCategoriaId(legajoId, anho, mes, categoriaId)
				.orElseThrow(() -> new CargoLiquidacionNotFoundException(legajoId, anho, mes, categoriaId));
	}

	public CargoLiquidacion add(CargoLiquidacion cargoLiquidacion) {
		cargoLiquidacion = repository.save(cargoLiquidacion);
		return cargoLiquidacion;
	}

	public CargoLiquidacion update(CargoLiquidacion newCargoLiquidacion, Long cargoLiquidacionId) {
		return repository.findByCargoLiquidacionId(cargoLiquidacionId).map(cargoLiquidacion -> {
			cargoLiquidacion = new CargoLiquidacion(null, newCargoLiquidacion.getLegajoId(),
					newCargoLiquidacion.getAnho(), newCargoLiquidacion.getMes(), newCargoLiquidacion.getDependenciaId(),
					newCargoLiquidacion.getFechaDesde(), newCargoLiquidacion.getFechaHasta(),
					newCargoLiquidacion.getCategoriaId(), newCargoLiquidacion.getCategoriaNombre(),
					newCargoLiquidacion.getCategoriaBasico(), newCargoLiquidacion.getHorasJornada(),
					newCargoLiquidacion.getJornada(), newCargoLiquidacion.getPresentismo(),
					newCargoLiquidacion.getSituacion(), newCargoLiquidacion.getPersona(),
					newCargoLiquidacion.getDependencia(), newCargoLiquidacion.getCategoria());
			cargoLiquidacion = repository.save(cargoLiquidacion);
			return cargoLiquidacion;
		}).orElseThrow(() -> new CargoLiquidacionNotFoundException(cargoLiquidacionId));
	}

	@Transactional
	public List<CargoLiquidacion> saveAll(List<CargoLiquidacion> cargos, Integer version, Boolean withVersion) {
		cargos = repository.saveAll(cargos);
		if (withVersion) {
			List<CargoLiquidacionVersion> backups = new ArrayList<CargoLiquidacionVersion>();
			for (CargoLiquidacion cargoLiquidacion : cargos) {
				backups.add(
						new CargoLiquidacionVersion(null, cargoLiquidacion.getLegajoId(), cargoLiquidacion.getAnho(),
								cargoLiquidacion.getMes(), version, cargoLiquidacion.getDependenciaId(),
								cargoLiquidacion.getCategoriaId(), cargoLiquidacion.getCategoriaBasico(),
								cargoLiquidacion.getHorasJornada(), cargoLiquidacion.getJornada(),
								cargoLiquidacion.getPresentismo(), cargoLiquidacion.getFechaDesde(),
								cargoLiquidacion.getFechaHasta(), cargoLiquidacion.getSituacion()));
			}
			backups = cargoLiquidacionVersionService.saveAll(backups);
		}
		return cargos;
	}

	@Transactional
	public void deleteAllNotInByPeriodo(List<Long> legajos, Integer anho, Integer mes) {
		repository.deleteAllByLegajoIdNotInAndAnhoAndMes(legajos, anho, mes);
	}

	@Transactional
	public void deleteByPeriodo(Integer anho, Integer mes) {
		repository.deleteAllByAnhoAndMes(anho, mes);
	}

	@Transactional
	public void deleteAllCargosDocentes(Long legajoId, Integer anho, Integer mes) {
		List<Integer> categoriaIds = categoriaService.findAllDocentes().stream().map(c -> c.getCategoriaId())
				.collect(Collectors.toList());
		repository.deleteAllByLegajoIdAndAnhoAndMesAndSituacionAndCategoriaIdIn(legajoId, anho, mes, "A", categoriaIds);
	}

	@Transactional
	public void deleteAllCargosNoDocentes(Long legajoId, Integer anho, Integer mes) {
		List<Integer> categoriaIds = categoriaService.findAllNoDocentes().stream().map(c -> c.getCategoriaId())
				.collect(Collectors.toList());
		repository.deleteAllByLegajoIdAndAnhoAndMesAndSituacionAndCategoriaIdIn(legajoId, anho, mes, "A", categoriaIds);
	}

}
