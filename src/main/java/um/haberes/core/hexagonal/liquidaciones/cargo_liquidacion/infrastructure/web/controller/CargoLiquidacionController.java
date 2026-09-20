/**
 *
 */
package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.exception.CargoLiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service.CargoLiquidacionService;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.web.dto.CargoLiquidacionPeriodoResponse;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.web.dto.CargoLiquidacionRequest;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.web.dto.CargoLiquidacionResponse;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.web.mapper.CargoLiquidacionDtoMapper;
import um.haberes.core.service.view.CargoLiquidacionPeriodoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/cargoliquidacion")
public class CargoLiquidacionController {

	private final CargoLiquidacionService service;
	private final CargoLiquidacionDtoMapper cargoLiquidacionDtoMapper;
	private final CargoLiquidacionPeriodoService cargoLiquidacionPeriodoService;

	public CargoLiquidacionController(CargoLiquidacionService service, CargoLiquidacionDtoMapper cargoLiquidacionDtoMapper,
			CargoLiquidacionPeriodoService cargoLiquidacionPeriodoService) {
		this.service = service;
		this.cargoLiquidacionDtoMapper = cargoLiquidacionDtoMapper;
		this.cargoLiquidacionPeriodoService = cargoLiquidacionPeriodoService;
	}

	@GetMapping("/legajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<CargoLiquidacionResponse>> findAllByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
																  @PathVariable Integer mes) {
		return new ResponseEntity<>(toResponseList(service.findAllByLegajo(legajoId, anho, mes)), HttpStatus.OK);
	}

	@GetMapping("/legajodocente/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<CargoLiquidacionResponse>> findAllDocenteByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<>(toResponseList(service.findAllDocenteByLegajo(legajoId, anho, mes)), HttpStatus.OK);
	}

	@GetMapping("/legajodocente/{legajoId}/{anho}/{mes}/facultad/{facultadId}")
	public ResponseEntity<List<CargoLiquidacionResponse>> findAllDocenteByLegajoAndFacultad(@PathVariable Long legajoId,
																					@PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Integer facultadId) {
		return new ResponseEntity<>(toResponseList(service.findAllDocenteByLegajoAndFacultad(legajoId, anho, mes, facultadId)), HttpStatus.OK);
	}

	@GetMapping("/legajonodocente/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<CargoLiquidacionResponse>> findAllNoDocenteByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<>(toResponseList(service.findAllNoDocenteByLegajo(legajoId, anho, mes)), HttpStatus.OK);
	}

	@GetMapping("/legajonodocente/{legajoId}/{anho}/{mes}/facultad/{facultadId}")
	public ResponseEntity<List<CargoLiquidacionResponse>> findAllNoDocenteByLegajoAndFacultad(@PathVariable Long legajoId,
																					  @PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Integer facultadId) {
		return new ResponseEntity<>(toResponseList(service.findAllNoDocenteByLegajoAndFacultad(legajoId, anho, mes, facultadId)), HttpStatus.OK);
	}

	@GetMapping("/legajonodocentehist/{legajoId}")
	public ResponseEntity<List<CargoLiquidacionResponse>> findAllNoDocenteHistByLegajo(@PathVariable Long legajoId) {
		return new ResponseEntity<>(toResponseList(service.findAllNoDocenteHistByLegajo(legajoId)), HttpStatus.OK);
	}

	@GetMapping("/legajoadicionalhcs/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<CargoLiquidacionResponse>> findAllAdicionalHCSByLegajo(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(toResponseList(service.findAllAdicionalHCSByLegajo(legajoId, anho, mes)), HttpStatus.OK);
	}

	@GetMapping("/legajoresto/{legajoId}/{anho}/{mes}/{categoriaId}")
	public ResponseEntity<List<CargoLiquidacionPeriodoResponse>> findAllRestoByLegajo(@PathVariable Long legajoId,
																			  @PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Integer categoriaId) {
		return new ResponseEntity<>(cargoLiquidacionPeriodoService.findAllRestoByLegajo(legajoId, anho, mes, categoriaId).stream()
				.map(cargoLiquidacionDtoMapper::toPeriodoResponse)
				.collect(Collectors.toList()), HttpStatus.OK);
	}

	@GetMapping("/categorianodocente/{legajoId}/{anho}/{mes}/{categoriaId}")
	public ResponseEntity<CargoLiquidacionResponse> findByCategoriaNoDocente(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer categoriaId) {
		return new ResponseEntity<>(cargoLiquidacionDtoMapper.toResponse(service.findByCategoriaNoDocente(legajoId, anho, mes, categoriaId)),
				HttpStatus.OK);
	}

	@GetMapping("/{cargoId}")
	public ResponseEntity<CargoLiquidacionResponse> findByCargoId(@PathVariable Long cargoId) {
		try {
			return new ResponseEntity<>(cargoLiquidacionDtoMapper.toResponse(service.findByCargoId(cargoId)), HttpStatus.OK);
		} catch (CargoLiquidacionException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<CargoLiquidacionResponse> add(@Valid @RequestBody CargoLiquidacionRequest request) {
		CargoLiquidacion created = service.add(cargoLiquidacionDtoMapper.toDomain(request));
		return new ResponseEntity<>(cargoLiquidacionDtoMapper.toResponse(created), HttpStatus.OK);
	}

	@PutMapping("/{cargoLiquidacionId}")
	public ResponseEntity<CargoLiquidacionResponse> update(@Valid @RequestBody CargoLiquidacionRequest request, @PathVariable Long cargoLiquidacionId) {
		try {
			CargoLiquidacion updated = service.update(cargoLiquidacionDtoMapper.toDomain(request), cargoLiquidacionId);
			return new ResponseEntity<>(cargoLiquidacionDtoMapper.toResponse(updated), HttpStatus.OK);
		} catch (CargoLiquidacionException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping("/saveall/{version}")
	public ResponseEntity<List<CargoLiquidacionResponse>> saveall(@Valid @RequestBody List<CargoLiquidacionRequest> requests, @PathVariable Integer version) {
		List<CargoLiquidacion> cargos = requests.stream()
				.map(cargoLiquidacionDtoMapper::toDomain)
				.collect(Collectors.toList());
		return new ResponseEntity<>(toResponseList(service.saveAll(cargos, version, true)), HttpStatus.OK);
	}

	@DeleteMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<Void> deleteByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		service.deleteByPeriodo(anho, mes);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	private List<CargoLiquidacionResponse> toResponseList(List<CargoLiquidacion> cargos) {
		return cargos.stream()
				.map(cargoLiquidacionDtoMapper::toResponse)
				.collect(Collectors.toList());
	}

}
