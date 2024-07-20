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

import um.haberes.rest.kotlin.model.Facultad;
import um.haberes.rest.service.FacultadService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/facultad")
public class FacultadController {
	
	@Autowired
	private FacultadService service;
	
	@GetMapping("/")
	public ResponseEntity<List<Facultad>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/facultades")
	public ResponseEntity<List<Facultad>> findAllFacultades() {
		return new ResponseEntity<List<Facultad>>(service.findAllFacultades(), HttpStatus.OK);
	}

	@GetMapping("/{facultadId}")
	public ResponseEntity<Facultad> findByFacultadId(@PathVariable Integer facultadId) {
		return new ResponseEntity<Facultad>(service.findByFacultadId(facultadId), HttpStatus.OK);
	}

}
