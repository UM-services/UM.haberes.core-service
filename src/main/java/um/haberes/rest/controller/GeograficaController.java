/**
 * 
 */
package um.haberes.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import um.haberes.rest.kotlin.model.Geografica;
import um.haberes.rest.service.GeograficaService;

/**
 * @author dquinteros
 *
 */
@RestController
@RequestMapping("/api/haberes/core/geografica")
public class GeograficaController {
	
	private final GeograficaService service;

	@Autowired
	public GeograficaController(GeograficaService service) {
		this.service = service;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Geografica>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@PostMapping("/ids")
	public ResponseEntity<List<Geografica>> findAllByGeograficaIdIn(@RequestBody List<Integer> ids) {
		return new ResponseEntity<>(service.findAllByGeograficaIdIn(ids), HttpStatus.OK);
	}

	@GetMapping("/{geograficaId}")
	public ResponseEntity<Geografica> findByGeograficaId(@PathVariable Integer geograficaId) {
		return new ResponseEntity<>(service.findByGeograficaId(geograficaId), HttpStatus.OK);
	}
	
	@PutMapping("/{geograficaId}")
	public ResponseEntity<Geografica> update(@RequestBody Geografica geografica, @PathVariable Integer geograficaId) {
		return new ResponseEntity<>(service.update(geografica, geograficaId), HttpStatus.OK);
	}

}
