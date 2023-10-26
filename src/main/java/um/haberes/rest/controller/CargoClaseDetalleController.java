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

import um.haberes.rest.kotlin.model.CargoClaseDetalle;
import um.haberes.rest.service.CargoClaseDetalleService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/cargoclasedetalle")
public class CargoClaseDetalleController {

	@Autowired
	private CargoClaseDetalleService service;

	@GetMapping("/legajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<CargoClaseDetalle>> findAllByLegajo(@PathVariable Long legajoId,
																   @PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<CargoClaseDetalle>>(service.findAllByLegajo(legajoId, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/facultad/{facultadId}/{anho}/{mes}")
	public ResponseEntity<List<CargoClaseDetalle>> findAllByFacultad(@PathVariable Integer facultadId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<CargoClaseDetalle>>(service.findAllByFacultad(facultadId, anho, mes),
				HttpStatus.OK);
	}

	@GetMapping("/cargoclaseperiodo/{cargoclaseperiodoId}")
	public ResponseEntity<List<CargoClaseDetalle>> findAllByCargoclaseperiodo(@PathVariable Long cargoclaseperiodoId) {
		return new ResponseEntity<List<CargoClaseDetalle>>(service.findAllByCargoClasePeriodo(cargoclaseperiodoId),
				HttpStatus.OK);
	}

	@GetMapping("/cargoclase/{cargoclaseId}/{anho}/{mes}")
	public ResponseEntity<List<CargoClaseDetalle>> findAllByCargoclase(@PathVariable Long cargoclaseId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<CargoClaseDetalle>>(service.findAllByCargoClase(cargoclaseId, anho, mes),
				HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<CargoClaseDetalle> add(@RequestBody CargoClaseDetalle cargoclasedetalle) {
		return new ResponseEntity<CargoClaseDetalle>(service.add(cargoclasedetalle), HttpStatus.OK);
	}

	@PutMapping("/{cargoclasedetalleId}")
	public ResponseEntity<CargoClaseDetalle> update(@RequestBody CargoClaseDetalle cargoclasedetalle,
			@PathVariable Long cargoclasedetalleId) {
		return new ResponseEntity<CargoClaseDetalle>(service.update(cargoclasedetalle, cargoclasedetalleId),
				HttpStatus.OK);
	}

	@PutMapping("/")
	public ResponseEntity<List<CargoClaseDetalle>> saveall(@RequestBody List<CargoClaseDetalle> cargoclasedetalles) {
		return new ResponseEntity<List<CargoClaseDetalle>>(service.saveAll(cargoclasedetalles), HttpStatus.OK);
	}

	@DeleteMapping("/{cargoClaseDetalleId}")
	public ResponseEntity<Void> delete(@PathVariable Long cargoClaseDetalleId) {
		service.delete(cargoClaseDetalleId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
