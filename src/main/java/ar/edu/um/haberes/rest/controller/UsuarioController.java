/**
 * 
 */
package ar.edu.um.haberes.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ar.edu.um.haberes.rest.exception.UsuarioNotFoundException;
import ar.edu.um.haberes.rest.model.Usuario;
import ar.edu.um.haberes.rest.service.UsuarioService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@GetMapping("/{legajoId}")
	public ResponseEntity<Usuario> findByLegajoId(@PathVariable Long legajoId) {
		try {
			return new ResponseEntity<Usuario>(service.findByLegajoId(legajoId), HttpStatus.OK);
		} catch (UsuarioNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/lastlog/{legajoId}")
	public ResponseEntity<Usuario> updateLastLog(@PathVariable Long legajoId) {
		try {
			return new ResponseEntity<Usuario>(service.updateLastLog(legajoId), HttpStatus.OK);
		} catch (UsuarioNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping("/isuservalid")
	public ResponseEntity<Boolean> isUserValid(@RequestBody Usuario usuario) {
		return new ResponseEntity<Boolean>(service.isUserValid(usuario), HttpStatus.OK);
	}

	@PutMapping("/setpassword")
	public ResponseEntity<Void> setPassword(@RequestBody Usuario usuario) {
		service.setPassword(usuario);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
