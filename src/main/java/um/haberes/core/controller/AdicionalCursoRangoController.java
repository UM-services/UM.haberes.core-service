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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import um.haberes.core.exception.AdicionalCursoRangoException;
import um.haberes.core.kotlin.model.AdicionalCursoRango;
import um.haberes.core.service.AdicionalCursoRangoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/adicionalCursoRango")
public class AdicionalCursoRangoController {

	@Autowired
	private AdicionalCursoRangoService service;

	@GetMapping("/tabla/{adicionalCursoTablaId}")
	public ResponseEntity<List<AdicionalCursoRango>> findAllByAdicionalCursoTabla(
			@PathVariable Long adicionalCursoTablaId) {
		return new ResponseEntity<>(
				service.findAllByAdicionalCursoTabla(adicionalCursoTablaId), HttpStatus.OK);
	}

	@GetMapping("/{adicionalCursoRangoId}")
	public ResponseEntity<AdicionalCursoRango> findByAdicionalCursoRangoId(@PathVariable Long adicionalCursoRangoId) {
		try {
			return new ResponseEntity<>(service.findByAdicionalCursoRangoId(adicionalCursoRangoId),
					HttpStatus.OK);
		} catch (AdicionalCursoRangoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<AdicionalCursoRango> add(@RequestBody AdicionalCursoRango adicionalCursoRango) {
		return new ResponseEntity<>(service.add(adicionalCursoRango), HttpStatus.OK);
	}

	@DeleteMapping("/{adicionalCursoRangoId}")
	public ResponseEntity<Void> deleteByAdicionalCursoRangoId(@PathVariable Long adicionalCursoRangoId) {
		service.deleteByAdicionalCursoRangoId(adicionalCursoRangoId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
