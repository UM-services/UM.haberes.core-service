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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import um.haberes.rest.exception.ClaseException;
import um.haberes.rest.model.Clase;
import um.haberes.rest.service.ClaseService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping(value = "/clase")
public class ClaseController {

	@Autowired
	private ClaseService service;

	/*
	 * find
	 *
	 */
	@GetMapping("/")
	public @ResponseBody ResponseEntity<List<Clase>> findAll() {
		return new ResponseEntity<List<Clase>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{claseId}")
	public @ResponseBody ResponseEntity<Clase> findByClaseId(@PathVariable Integer claseId) {
		try {
			return new ResponseEntity<Clase>(service.findByClaseId(claseId), HttpStatus.OK);
		} catch (ClaseException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/last")
	public @ResponseBody ResponseEntity<Clase> findLast() {
		try {
			return new ResponseEntity<Clase>(service.findLast(), HttpStatus.OK);
		} catch (ClaseException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	/*
	 * delete
	 *
	 */
	@DeleteMapping("/{claseId}")
	public ResponseEntity<Void> delete(@PathVariable Integer claseId) {
		service.delete(claseId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	/*
	 * add
	 *
	 */
	@PostMapping("/")
	public ResponseEntity<Clase> add(@RequestBody Clase clase) {
		return new ResponseEntity<Clase>(service.add(clase), HttpStatus.OK);
	}

	/*
	 * update
	 *
	 */
	@PutMapping("/{claseId}")
	public ResponseEntity<Clase> update(@RequestBody Clase newClase, @PathVariable Integer claseId) {
		return new ResponseEntity<Clase>(service.update(newClase, claseId), HttpStatus.OK);
	}

}
