/**
 * 
 */
package ar.edu.um.haberes.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ar.edu.um.haberes.rest.exception.AdicionalCursoTablaNotFoundException;
import ar.edu.um.haberes.rest.model.AdicionalCursoTabla;
import ar.edu.um.haberes.rest.service.AdicionalCursoTablaService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/adicionalCursoTabla")
public class AdicionalCursoTablaController {

	@Autowired
	private AdicionalCursoTablaService service;
	
	@GetMapping("/")
	public ResponseEntity<List<AdicionalCursoTabla>> findAll() {
		return new ResponseEntity<List<AdicionalCursoTabla>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{adicionalCursoTablaId}")
	public ResponseEntity<AdicionalCursoTabla> findByAdicionalCursoTablaId(@PathVariable Long adicionalCursoTablaId) {
		try {
			return new ResponseEntity<AdicionalCursoTabla>(service.findByAdicionalCursoTablaId(adicionalCursoTablaId),
					HttpStatus.OK);
		} catch (AdicionalCursoTablaNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
