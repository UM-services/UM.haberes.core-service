/**
 * 
 */
package um.haberes.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import um.haberes.core.model.CargoClaseDetalleEntity;
import um.haberes.core.service.CargoClaseDetalleService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/cargoclasedetalle")
public class CargoClaseDetalleController {

	@Autowired
	private CargoClaseDetalleService service;

	@GetMapping("/legajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<CargoClaseDetalleEntity>> findAllByLegajo(@PathVariable Long legajoId,
																   @PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findAllByLegajo(legajoId, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/legajo/{legajoId}/{anho}/{mes}/facultad/{facultadId}")
	public ResponseEntity<List<CargoClaseDetalleEntity>> findAllByLegajoAndFacultad(@PathVariable Long legajoId, @PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Integer facultadId) {
		return new ResponseEntity<>(service.findAllByLegajoAndFacultad(legajoId, anho, mes, facultadId), HttpStatus.OK);
	}

	@GetMapping("/facultad/{facultadId}/{anho}/{mes}")
	public ResponseEntity<List<CargoClaseDetalleEntity>> findAllByFacultad(@PathVariable Integer facultadId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findAllByFacultad(facultadId, anho, mes),
				HttpStatus.OK);
	}

	@GetMapping("/cargoclaseperiodo/{cargoclaseperiodoId}")
	public ResponseEntity<List<CargoClaseDetalleEntity>> findAllByCargoClasePeriodo(@PathVariable Long cargoclaseperiodoId) {
		return new ResponseEntity<>(service.findAllByCargoClasePeriodo(cargoclaseperiodoId),
				HttpStatus.OK);
	}

	@GetMapping("/cargoclase/{cargoclaseId}/{anho}/{mes}")
	public ResponseEntity<List<CargoClaseDetalleEntity>> findAllByCargoclase(@PathVariable Long cargoclaseId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findAllByCargoClase(cargoclaseId, anho, mes),
				HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<CargoClaseDetalleEntity> add(@RequestBody CargoClaseDetalleEntity cargoclasedetalle) {
		return new ResponseEntity<>(service.add(cargoclasedetalle), HttpStatus.OK);
	}

	@PutMapping("/{cargoclasedetalleId}")
	public ResponseEntity<CargoClaseDetalleEntity> update(@RequestBody CargoClaseDetalleEntity cargoclasedetalle,
			@PathVariable Long cargoclasedetalleId) {
		return new ResponseEntity<>(service.update(cargoclasedetalle, cargoclasedetalleId),
				HttpStatus.OK);
	}

	@PutMapping("/")
	public ResponseEntity<List<CargoClaseDetalleEntity>> saveall(@RequestBody List<CargoClaseDetalleEntity> cargoclasedetalles) {
		return new ResponseEntity<>(service.saveAll(cargoclasedetalles), HttpStatus.OK);
	}

	@DeleteMapping("/{cargoClaseDetalleId}")
	public ResponseEntity<Void> delete(@PathVariable Long cargoClaseDetalleId) {
		service.delete(cargoClaseDetalleId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
