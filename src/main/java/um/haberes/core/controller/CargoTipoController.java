/**
 * 
 */
package um.haberes.core.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.kotlin.model.CargoTipo;
import um.haberes.core.service.CargoTipoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/cargotipo")
public class CargoTipoController {

	private final CargoTipoService service;

	public CargoTipoController(CargoTipoService service) {
		this.service = service;
	}

	@GetMapping("/")
	public ResponseEntity<List<CargoTipo>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{cargoTipoId}")
	public ResponseEntity<CargoTipo> findByCargoTipoId(@PathVariable Integer cargoTipoId) {
		return new ResponseEntity<CargoTipo>(service.findByCargoTipoId(cargoTipoId), HttpStatus.OK);
	}

}
