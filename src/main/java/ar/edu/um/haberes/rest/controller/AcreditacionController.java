/**
 * 
 */
package ar.edu.um.haberes.rest.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ar.edu.um.haberes.rest.exception.AcreditacionNotFoundException;
import ar.edu.um.haberes.rest.model.Acreditacion;
import ar.edu.um.haberes.rest.service.AcreditacionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping(value = "/acreditacion")
public class AcreditacionController {
	@Autowired
	private AcreditacionService service;

	@GetMapping("/")
	public @ResponseBody ResponseEntity<List<Acreditacion>> findAll() {
		return new ResponseEntity<List<Acreditacion>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{acreditacionId}")
	public @ResponseBody ResponseEntity<Acreditacion> findByAcreditacionId(@PathVariable Long acreditacionId) {
		try {
			return new ResponseEntity<Acreditacion>(service.findByAcreditacionId(acreditacionId), HttpStatus.OK);
		} catch (AcreditacionNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/periodo/{anho}/{mes}")
	public @ResponseBody ResponseEntity<Acreditacion> findByPeriodo(@PathVariable Integer anho,
			@PathVariable Integer mes) {
		try {
			return new ResponseEntity<Acreditacion>(service.findByPeriodo(anho, mes), HttpStatus.OK);
		} catch (AcreditacionNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("/{acreditacionId}")
	public ResponseEntity<Void> delete(@PathVariable Long acreditacionId) {
		service.delete(acreditacionId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/")
	public ResponseEntity<Acreditacion> add(@RequestBody Acreditacion Acreditacion) {
		try {
			return new ResponseEntity<Acreditacion>(service.add(Acreditacion), HttpStatus.OK);
		} catch (AcreditacionNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@PutMapping("/{acreditacionId}")
	public ResponseEntity<Acreditacion> update(@RequestBody Acreditacion acreditacion,
			@PathVariable Long acreditacionId) {
		try {
			return new ResponseEntity<Acreditacion>(service.update(acreditacion, acreditacionId), HttpStatus.OK);
		} catch (AcreditacionNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
