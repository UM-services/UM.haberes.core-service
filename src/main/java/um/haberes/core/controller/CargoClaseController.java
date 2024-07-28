/**
 * 
 */
package um.haberes.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.kotlin.model.CargoClase;
import um.haberes.core.service.CargoClaseService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/cargoclase")
public class CargoClaseController {

	@Autowired
	private CargoClaseService service;

	@GetMapping("/")
	public ResponseEntity<List<CargoClase>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{cargoClaseId}")
	public ResponseEntity<CargoClase> findByCargoClaseId(@PathVariable Long cargoClaseId) {
		return new ResponseEntity<>(service.findByCargoClaseId(cargoClaseId), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<CargoClase> add(@RequestBody CargoClase cargoClase) {
		return new ResponseEntity<>(service.add(cargoClase), HttpStatus.OK);
	}

	@PutMapping("/{cargoClaseId}")
	public ResponseEntity<CargoClase> update(@RequestBody CargoClase cargoClase, @PathVariable Long cargoClaseId) {
		return new ResponseEntity<>(service.update(cargoClase, cargoClaseId), HttpStatus.OK);
	}

}
