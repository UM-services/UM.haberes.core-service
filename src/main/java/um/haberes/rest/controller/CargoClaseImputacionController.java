/**
 * 
 */
package um.haberes.rest.controller;

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

import um.haberes.rest.model.CargoClaseImputacion;
import um.haberes.rest.service.CargoClaseImputacionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/cargoclaseimputacion")
public class CargoClaseImputacionController {

	@Autowired
	private CargoClaseImputacionService service;

	@GetMapping("/")
	public ResponseEntity<List<CargoClaseImputacion>> findAll() {
		return new ResponseEntity<List<CargoClaseImputacion>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{cargoclaseimputacionId}")
	public ResponseEntity<CargoClaseImputacion> findByCargoclaseimputacionId(
			@PathVariable Long cargoclaseimputacionId) {
		return new ResponseEntity<CargoClaseImputacion>(service.findByCargoclaseimputacionId(cargoclaseimputacionId),
				HttpStatus.OK);
	}

	@GetMapping("/unique/{dependenciaId}/{facultadId}/{geograficaId}/{cargoclaseId}")
	public ResponseEntity<CargoClaseImputacion> findByUnique(@PathVariable Integer dependenciaId,
			@PathVariable Integer facultadId, @PathVariable Integer geograficaId, @PathVariable Long cargoclaseId) {
		return new ResponseEntity<CargoClaseImputacion>(
				service.findByUnique(dependenciaId, facultadId, geograficaId, cargoclaseId), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<CargoClaseImputacion> add(@RequestBody CargoClaseImputacion cargoclaseimputacion) {
		return new ResponseEntity<CargoClaseImputacion>(service.add(cargoclaseimputacion), HttpStatus.OK);
	}

	@PutMapping("/{cargoclaseimputacionId}")
	public ResponseEntity<CargoClaseImputacion> update(@RequestBody CargoClaseImputacion cargoclaseimputacion,
			@PathVariable Long cargoclaseimputacionId) {
		return new ResponseEntity<CargoClaseImputacion>(service.update(cargoclaseimputacion, cargoclaseimputacionId),
				HttpStatus.OK);
	}

}
