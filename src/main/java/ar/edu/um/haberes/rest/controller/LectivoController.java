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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.um.haberes.rest.model.Lectivo;
import ar.edu.um.haberes.rest.service.LectivoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/lectivo")
public class LectivoController {

	@Autowired
	private LectivoService service;

	@GetMapping("/")
	public ResponseEntity<List<Lectivo>> findAll() {
		return new ResponseEntity<List<Lectivo>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/reverse")
	public ResponseEntity<List<Lectivo>> findAllReverse() {
		return new ResponseEntity<List<Lectivo>>(service.findAllReverse(), HttpStatus.OK);
	}

	@GetMapping("/{lectivoId}")
	public ResponseEntity<Lectivo> findByLectivoId(@PathVariable Integer lectivoId) {
		return new ResponseEntity<Lectivo>(service.findByLectivoId(lectivoId), HttpStatus.OK);
	}

	@PutMapping("/saveall")
	public ResponseEntity<List<Lectivo>> saveAll(@RequestBody List<Lectivo> lectivos) {
		return new ResponseEntity<List<Lectivo>>(service.saveAll(lectivos), HttpStatus.OK);
	}

}
