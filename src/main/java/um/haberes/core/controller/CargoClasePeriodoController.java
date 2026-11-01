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

import um.haberes.core.model.CargoClasePeriodoEntity;
import um.haberes.core.service.CargoClasePeriodoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/cargoclaseperiodo")
public class CargoClasePeriodoController {

	@Autowired
	private CargoClasePeriodoService service;

	@GetMapping("/facultad/{facultadId}")
	public ResponseEntity<List<CargoClasePeriodoEntity>> findAllByFacultad(@PathVariable Integer facultadId) {
		return new ResponseEntity<List<CargoClasePeriodoEntity>>(service.findAllByFacultad(facultadId), HttpStatus.OK);
	}

	@GetMapping("/legajo/{legajoId}")
	public ResponseEntity<List<CargoClasePeriodoEntity>> findAllByLegajo(@PathVariable Long legajoId) {
		return new ResponseEntity<List<CargoClasePeriodoEntity>>(service.findAllByLegajo(legajoId), HttpStatus.OK);
	}

	@GetMapping("/{cargoClasePeriodoId}")
	public ResponseEntity<CargoClasePeriodoEntity> findByCargoclaseperiodoId(@PathVariable Long cargoClasePeriodoId) {
		return new ResponseEntity<CargoClasePeriodoEntity>(service.findByCargoClasePeriodoId(cargoClasePeriodoId),
				HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<CargoClasePeriodoEntity> add(@RequestBody CargoClasePeriodoEntity cargoClasePeriodo) {
		return new ResponseEntity<CargoClasePeriodoEntity>(service.add(cargoClasePeriodo), HttpStatus.OK);
	}

	@PutMapping("/{cargoClasePeriodoId}")
	public ResponseEntity<CargoClasePeriodoEntity> update(@RequestBody CargoClasePeriodoEntity cargoClasePeriodo,
			@PathVariable Long cargoClasePeriodoId) {
		return new ResponseEntity<CargoClasePeriodoEntity>(service.update(cargoClasePeriodo, cargoClasePeriodoId),
				HttpStatus.OK);
	}

	@DeleteMapping("/{cargoClasePeriodoId}")
	public ResponseEntity<Void> delete(@PathVariable Long cargoClasePeriodoId) {
		service.delete(cargoClasePeriodoId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
