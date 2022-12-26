/**
 * 
 */
package ar.edu.um.haberes.rest.controller.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.um.haberes.rest.service.facade.ContableService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/contable")
public class ContableController {

	@Autowired
	private ContableService service;

	@GetMapping("/generatelegajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Void> generateByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		service.generateByLegajo(legajoId, anho, mes);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/deletelegajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Void> deleteAllByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		service.deleteAllByLegajo(legajoId, anho, mes);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/deleteperiodo/{anho}/{mes}")
	public ResponseEntity<Void> deleteAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		service.deleteAllByPeriodo(anho, mes);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
