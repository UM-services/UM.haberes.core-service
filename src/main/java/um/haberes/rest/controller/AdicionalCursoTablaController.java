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
import org.springframework.web.server.ResponseStatusException;

import um.haberes.rest.exception.AdicionalCursoTablaException;
import um.haberes.rest.kotlin.model.AdicionalCursoTabla;
import um.haberes.rest.service.AdicionalCursoTablaService;

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
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{adicionalCursoTablaId}")
	public ResponseEntity<AdicionalCursoTabla> findByAdicionalCursoTablaId(@PathVariable Long adicionalCursoTablaId) {
		try {
			return new ResponseEntity<>(service.findByAdicionalCursoTablaId(adicionalCursoTablaId),
					HttpStatus.OK);
		} catch (AdicionalCursoTablaException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
