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
import org.springframework.web.server.ResponseStatusException;

import um.haberes.core.exception.CargoLiquidacionException;
import um.haberes.core.kotlin.model.Cargo;
import um.haberes.core.service.CargoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/cargo")
public class CargoController {

	@Autowired
	private CargoService service;

	@GetMapping("/legajo/{legajoId}")
	public ResponseEntity<List<Cargo>> findAllByLegajoId(@PathVariable Long legajoId) {
		return new ResponseEntity<List<Cargo>>(service.findAllByLegajoId(legajoId), HttpStatus.OK);
	}

	@GetMapping("/{cargoId}")
	public ResponseEntity<Cargo> findByCargoId(@PathVariable Long cargoId) {
		try {
			return new ResponseEntity<Cargo>(service.findByCargoId(cargoId), HttpStatus.OK);
		} catch (CargoLiquidacionException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<Cargo> add(@RequestBody Cargo cargo) {
		return new ResponseEntity<Cargo>(service.add(cargo), HttpStatus.OK);
	}

	@PutMapping("/{cargoId}")
	public ResponseEntity<Cargo> update(@RequestBody Cargo cargo, @PathVariable Long cargoId) {
		return new ResponseEntity<Cargo>(service.update(cargo, cargoId), HttpStatus.OK);
	}

	@DeleteMapping("/{cargoId}")
	public ResponseEntity<Void> delete(@PathVariable Long cargoId) {
		service.delete(cargoId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
