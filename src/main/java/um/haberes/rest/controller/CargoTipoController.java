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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.model.CargoTipo;
import um.haberes.rest.service.CargoTipoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/cargotipo")
public class CargoTipoController {

	@Autowired
	private CargoTipoService service;

	@GetMapping("/")
	public ResponseEntity<List<CargoTipo>> findAll() {
		return new ResponseEntity<List<CargoTipo>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{cargoTipoId}")
	public ResponseEntity<CargoTipo> findByCargoTipoId(@PathVariable Integer cargoTipoId) {
		return new ResponseEntity<CargoTipo>(service.findByCargoTipoId(cargoTipoId), HttpStatus.OK);
	}

}
