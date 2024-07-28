/**
 * 
 */
package um.haberes.core.controller;

import java.util.List;

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

import um.haberes.core.exception.CargoLiquidacionException;
import um.haberes.core.kotlin.model.CargoLiquidacion;
import um.haberes.core.kotlin.model.view.CargoLiquidacionPeriodo;
import um.haberes.core.service.CargoLiquidacionService;
import um.haberes.core.service.view.CargoLiquidacionPeriodoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/cargoliquidacion")
public class CargoLiquidacionController {

	private final CargoLiquidacionService service;
	private final CargoLiquidacionPeriodoService cargoLiquidacionPeriodoService;

	public CargoLiquidacionController(CargoLiquidacionService service, CargoLiquidacionPeriodoService cargoLiquidacionPeriodoService) {
		this.service = service;
		this.cargoLiquidacionPeriodoService = cargoLiquidacionPeriodoService;
	}

	@GetMapping("/legajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<CargoLiquidacion>> findAllByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
																  @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findAllByLegajo(legajoId, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/legajodocente/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<CargoLiquidacion>> findAllDocenteByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<>(service.findAllDocenteByLegajo(legajoId, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/legajodocente/{legajoId}/{anho}/{mes}/facultad/{facultadId}")
	public ResponseEntity<List<CargoLiquidacion>> findAllDocenteByLegajoAndFacultad(@PathVariable Long legajoId,
																					@PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Integer facultadId) {
		return new ResponseEntity<>(service.findAllDocenteByLegajoAndFacultad(legajoId, anho, mes, facultadId), HttpStatus.OK);
	}

	@GetMapping("/legajonodocente/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<CargoLiquidacion>> findAllNoDocenteByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<>(service.findAllNoDocenteByLegajo(legajoId, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/legajonodocente/{legajoId}/{anho}/{mes}/facultad/{facultadId}")
	public ResponseEntity<List<CargoLiquidacion>> findAllNoDocenteByLegajoAndFacultad(@PathVariable Long legajoId,
																					  @PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Integer facultadId) {
		return new ResponseEntity<>(service.findAllNoDocenteByLegajoAndFacultad(legajoId, anho, mes, facultadId), HttpStatus.OK);
	}

	@GetMapping("/legajonodocentehist/{legajoId}")
	public ResponseEntity<List<CargoLiquidacion>> findAllNoDocenteHistByLegajo(@PathVariable Long legajoId) {
		return new ResponseEntity<>(service.findAllNoDocenteHistByLegajo(legajoId), HttpStatus.OK);
	}

	@GetMapping("/legajoadicionalhcs/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<CargoLiquidacion>> findAllAdicionalHCSByLegajo(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findAllAdicionalHCSByLegajo(legajoId, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/legajoresto/{legajoId}/{anho}/{mes}/{categoriaId}")
	public ResponseEntity<List<CargoLiquidacionPeriodo>> findAllRestoByLegajo(@PathVariable Long legajoId,
																			  @PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Integer categoriaId) {
		return new ResponseEntity<>(
				cargoLiquidacionPeriodoService.findAllRestoByLegajo(legajoId, anho, mes, categoriaId), HttpStatus.OK);
	}

	@GetMapping("/categorianodocente/{legajoId}/{anho}/{mes}/{categoriaId}")
	public ResponseEntity<CargoLiquidacion> findByCategoriaNoDocente(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer categoriaId) {
		return new ResponseEntity<>(service.findByCategoriaNoDocente(legajoId, anho, mes, categoriaId),
				HttpStatus.OK);
	}
	
	@GetMapping("/{cargoId}")
	public ResponseEntity<CargoLiquidacion> findByCargoId(@PathVariable Long cargoId) {
		try {
			return new ResponseEntity<>(service.findByCargoId(cargoId), HttpStatus.OK);
		} catch (CargoLiquidacionException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<CargoLiquidacion> add(@RequestBody CargoLiquidacion cargoLiquidacion) {
		return new ResponseEntity<>(service.add(cargoLiquidacion), HttpStatus.OK);
	}

	@PutMapping("/{cargoLiquidacionId}")
	public ResponseEntity<CargoLiquidacion> update(@RequestBody CargoLiquidacion cargoLiquidacion, @PathVariable Long cargoLiquidacionId) {
		return new ResponseEntity<>(service.update(cargoLiquidacion, cargoLiquidacionId), HttpStatus.OK);
	}

	@PutMapping("/saveall/{version}")
	public ResponseEntity<List<CargoLiquidacion>> saveall(@RequestBody List<CargoLiquidacion> cargos, @PathVariable Integer version) {
		return new ResponseEntity<>(service.saveAll(cargos, version, true), HttpStatus.OK);
	}

	@DeleteMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<Void> deleteByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		service.deleteByPeriodo(anho, mes);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
