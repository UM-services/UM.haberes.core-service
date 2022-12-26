/**
 * 
 */
package ar.edu.um.haberes.rest.controller;

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

import ar.edu.um.haberes.rest.model.CargoClase;
import ar.edu.um.haberes.rest.service.CargoClaseService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/cargoclase")
public class CargoClaseController {

	@Autowired
	private CargoClaseService service;

	@GetMapping("/")
	public ResponseEntity<List<CargoClase>> findAll() {
		return new ResponseEntity<List<CargoClase>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{cargoClaseId}")
	public ResponseEntity<CargoClase> findByCargoClaseId(@PathVariable Long cargoClaseId) {
		return new ResponseEntity<CargoClase>(service.findByCargoClaseId(cargoClaseId), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<CargoClase> add(@RequestBody CargoClase cargoClase) {
		return new ResponseEntity<CargoClase>(service.add(cargoClase), HttpStatus.OK);
	}

	@PutMapping("/{cargoClaseId}")
	public ResponseEntity<CargoClase> update(@RequestBody CargoClase cargoClase, @PathVariable Long cargoClaseId) {
		return new ResponseEntity<CargoClase>(service.update(cargoClase, cargoClaseId), HttpStatus.OK);
	}

}
