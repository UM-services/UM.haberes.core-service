/**
 * 
 */
package um.haberes.core.controller.facade;

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

import um.haberes.core.kotlin.model.CargoClasePeriodo;
import um.haberes.core.service.facade.CargoClaseToolService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/cargoclasetool")
public class CargoClaseToolController {

	private final CargoClaseToolService service;

	@Autowired
	public CargoClaseToolController(CargoClaseToolService service) {
		this.service = service;
	}

	@PostMapping("/")
	public ResponseEntity<String> addCargo(@RequestBody CargoClasePeriodo cargoclaseperiodo) {
		return new ResponseEntity<>(service.addCargo(cargoclaseperiodo), HttpStatus.OK);
	}

	@DeleteMapping("/{cargoClasePeriodoId}")
	public ResponseEntity<String> deleteCargo(@PathVariable Long cargoClasePeriodoId) {
		return new ResponseEntity<>(service.deleteCargo(cargoClasePeriodoId), HttpStatus.OK);
	}

	@PutMapping("/{cargoClasePeriodoId}/{anhoHasta}/{mesHasta}")
	public ResponseEntity<String> changeHasta(@PathVariable Long cargoClasePeriodoId, @PathVariable Integer anhoHasta,
			@PathVariable Integer mesHasta) {
		return new ResponseEntity<>(service.changeHasta(cargoClasePeriodoId, anhoHasta, mesHasta), HttpStatus.OK);
	}

	@GetMapping("/update/periodo/{anho}/{mes}")
	public ResponseEntity<Void> updateValorHora(@PathVariable Integer anho, @PathVariable Integer mes) {
		service.updateValorHora(anho, mes);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
