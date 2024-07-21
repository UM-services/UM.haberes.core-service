/**
 * 
 */
package um.haberes.rest.controller;

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

import um.haberes.rest.kotlin.model.CargoClasePeriodo;
import um.haberes.rest.service.CargoClasePeriodoService;

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
	public ResponseEntity<List<CargoClasePeriodo>> findAllByFacultad(@PathVariable Integer facultadId) {
		return new ResponseEntity<List<CargoClasePeriodo>>(service.findAllByFacultad(facultadId), HttpStatus.OK);
	}

	@GetMapping("/legajo/{legajoId}")
	public ResponseEntity<List<CargoClasePeriodo>> findAllByLegajo(@PathVariable Long legajoId) {
		return new ResponseEntity<List<CargoClasePeriodo>>(service.findAllByLegajo(legajoId), HttpStatus.OK);
	}

	@GetMapping("/{cargoClasePeriodoId}")
	public ResponseEntity<CargoClasePeriodo> findByCargoclaseperiodoId(@PathVariable Long cargoClasePeriodoId) {
		return new ResponseEntity<CargoClasePeriodo>(service.findByCargoClasePeriodoId(cargoClasePeriodoId),
				HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<CargoClasePeriodo> add(@RequestBody CargoClasePeriodo cargoClasePeriodo) {
		return new ResponseEntity<CargoClasePeriodo>(service.add(cargoClasePeriodo), HttpStatus.OK);
	}

	@PutMapping("/{cargoClasePeriodoId}")
	public ResponseEntity<CargoClasePeriodo> update(@RequestBody CargoClasePeriodo cargoClasePeriodo,
			@PathVariable Long cargoClasePeriodoId) {
		return new ResponseEntity<CargoClasePeriodo>(service.update(cargoClasePeriodo, cargoClasePeriodoId),
				HttpStatus.OK);
	}

	@DeleteMapping("/{cargoClasePeriodoId}")
	public ResponseEntity<Void> delete(@PathVariable Long cargoClasePeriodoId) {
		service.delete(cargoClasePeriodoId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
