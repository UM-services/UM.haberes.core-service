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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ar.edu.um.haberes.rest.exception.AdicionalCursoRangoNotFoundException;
import ar.edu.um.haberes.rest.model.AdicionalCursoRango;
import ar.edu.um.haberes.rest.service.AdicionalCursoRangoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/adicionalCursoRango")
public class AdicionalCursoRangoController {

	@Autowired
	private AdicionalCursoRangoService service;

	@GetMapping("/tabla/{adicionalCursoTablaId}")
	public ResponseEntity<List<AdicionalCursoRango>> findAllByAdicionalCursoTabla(
			@PathVariable Long adicionalCursoTablaId) {
		return new ResponseEntity<List<AdicionalCursoRango>>(
				service.findAllByAdicionalCursoTabla(adicionalCursoTablaId), HttpStatus.OK);
	}

	@GetMapping("/{adicionalCursoRangoId}")
	public ResponseEntity<AdicionalCursoRango> findByAdicionalCursoRangoId(@PathVariable Long adicionalCursoRangoId) {
		try {
			return new ResponseEntity<AdicionalCursoRango>(service.findByAdicionalCursoRangoId(adicionalCursoRangoId),
					HttpStatus.OK);
		} catch (AdicionalCursoRangoNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<AdicionalCursoRango> add(@RequestBody AdicionalCursoRango adicionalCursoRango) {
		return new ResponseEntity<AdicionalCursoRango>(service.add(adicionalCursoRango), HttpStatus.OK);
	}

	@DeleteMapping("/{adicionalCursoRangoId}")
	public ResponseEntity<Void> deleteByAdicionalCursoRangoId(@PathVariable Long adicionalCursoRangoId) {
		service.deleteByAdicionalCursoRangoId(adicionalCursoRangoId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
