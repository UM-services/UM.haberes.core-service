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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import um.haberes.core.exception.ClaseException;
import um.haberes.core.model.ClaseEntity;
import um.haberes.core.service.ClaseService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/clase")
public class ClaseController {

	private final ClaseService service;

	@Autowired
	public ClaseController(ClaseService service) {
		this.service = service;
	}

	@GetMapping("/")
	public @ResponseBody ResponseEntity<List<ClaseEntity>> findAll() {
		return new ResponseEntity<List<ClaseEntity>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{claseId}")
	public @ResponseBody ResponseEntity<ClaseEntity> findByClaseId(@PathVariable Integer claseId) {
		try {
			return new ResponseEntity<ClaseEntity>(service.findByClaseId(claseId), HttpStatus.OK);
		} catch (ClaseException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/last")
	public @ResponseBody ResponseEntity<ClaseEntity> findLast() {
		try {
			return new ResponseEntity<ClaseEntity>(service.findLast(), HttpStatus.OK);
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
	public ResponseEntity<ClaseEntity> add(@RequestBody ClaseEntity clase) {
		return new ResponseEntity<ClaseEntity>(service.add(clase), HttpStatus.OK);
	}

	/*
	 * update
	 *
	 */
	@PutMapping("/{claseId}")
	public ResponseEntity<ClaseEntity> update(@RequestBody ClaseEntity newClase, @PathVariable Integer claseId) {
		return new ResponseEntity<ClaseEntity>(service.update(newClase, claseId), HttpStatus.OK);
	}

}
