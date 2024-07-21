/**
 * 
 */
package um.haberes.rest.controller;

import java.util.List;

import um.haberes.rest.kotlin.model.Contacto;
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

import um.haberes.rest.exception.ContactoException;
import um.haberes.rest.service.ContactoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/contacto")
public class ContactoController {

	@Autowired
	private ContactoService service;

	@GetMapping("/")
	public ResponseEntity<List<Contacto>> findAll() {
		return new ResponseEntity<List<Contacto>>(service.findAll(), HttpStatus.OK);
	}

	@PostMapping("/legajos")
	public ResponseEntity<List<Contacto>> findAllLegajos(@RequestBody List<Long> legajos) {
		return new ResponseEntity<List<Contacto>>(service.findAllLegajos(legajos), HttpStatus.OK);
	}

	@GetMapping("/{legajoId}")
	public ResponseEntity<Contacto> findByLegajoId(@PathVariable Long legajoId) {
		try {
			return new ResponseEntity<Contacto>(service.findByLegajoId(legajoId), HttpStatus.OK);
		} catch (ContactoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<Contacto> add(@RequestBody Contacto contacto) {
		return new ResponseEntity<Contacto>(service.add(contacto), HttpStatus.OK);
	}

	@PutMapping("/{legajoId}")
	public ResponseEntity<Contacto> update(@RequestBody Contacto contacto, @PathVariable Long legajoId) {
		return new ResponseEntity<Contacto>(service.update(contacto, legajoId), HttpStatus.OK);
	}

	@DeleteMapping("/{legajoId}")
	public ResponseEntity<Void> delete(@PathVariable Long legajoId) {
		service.delete(legajoId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
