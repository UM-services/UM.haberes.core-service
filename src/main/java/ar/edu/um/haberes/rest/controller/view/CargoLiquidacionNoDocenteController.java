/**
 * 
 */
package ar.edu.um.haberes.rest.controller.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.um.haberes.rest.model.view.CargoLiquidacionNoDocente;
import ar.edu.um.haberes.rest.service.view.CargoLiquidacionNoDocenteService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/cargoLiquidacionNoDocente")
public class CargoLiquidacionNoDocenteController {

	@Autowired
	private CargoLiquidacionNoDocenteService service;

	@GetMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<List<CargoLiquidacionNoDocente>> findAllByPeriodo(@PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<CargoLiquidacionNoDocente>>(service.findAllByPeriodo(anho, mes), HttpStatus.OK);
	}

}
