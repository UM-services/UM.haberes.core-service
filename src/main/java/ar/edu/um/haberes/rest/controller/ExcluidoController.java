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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ar.edu.um.haberes.rest.exception.ExcluidoNotFoundException;
import ar.edu.um.haberes.rest.model.Excluido;
import ar.edu.um.haberes.rest.service.ExcluidoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/excluido")
public class ExcluidoController {

	@Autowired
	private ExcluidoService service;

	@GetMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<List<Excluido>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<Excluido>>(service.findAllByPeriodo(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/unique/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Excluido> findByUnique(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		try {
			return new ResponseEntity<Excluido>(service.findByUnique(legajoId, anho, mes), HttpStatus.OK);
		} catch (ExcluidoNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<Excluido> add(@RequestBody Excluido excluido) {
		return new ResponseEntity<Excluido>(service.add(excluido), HttpStatus.OK);
	}

	@PutMapping("/{excluidoId}")
	public ResponseEntity<Excluido> update(@RequestBody Excluido excluido, @PathVariable Long excluidoId) {
		return new ResponseEntity<Excluido>(service.update(excluido, excluidoId), HttpStatus.OK);
	}

	@DeleteMapping("/unique/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Void> deleteByUnique(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		service.deleteByUnique(legajoId, anho, mes);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
