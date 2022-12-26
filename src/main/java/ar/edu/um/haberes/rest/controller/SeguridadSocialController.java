/**
 * 
 */
package ar.edu.um.haberes.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ar.edu.um.haberes.rest.exception.SeguridadSocialNotFoundException;
import ar.edu.um.haberes.rest.model.SeguridadSocial;
import ar.edu.um.haberes.rest.service.SeguridadSocialService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/seguridadSocial")
public class SeguridadSocialController {

	@Autowired
	private SeguridadSocialService service;

	@GetMapping("/unique/{anho}/{mes}")
	public ResponseEntity<SeguridadSocial> findByUnique(@PathVariable Integer anho, @PathVariable Integer mes) {
		try {
			return new ResponseEntity<SeguridadSocial>(service.findByUnique(anho, mes), HttpStatus.OK);
		} catch (SeguridadSocialNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<SeguridadSocial> add(@RequestBody SeguridadSocial seguridadSocial) {
		return new ResponseEntity<SeguridadSocial>(service.add(seguridadSocial), HttpStatus.OK);
	}

	@PutMapping("/{seguridadSocialId}")
	public ResponseEntity<SeguridadSocial> update(@RequestBody SeguridadSocial seguridadSocial,
			@PathVariable Long seguridadSocialId) {
		return new ResponseEntity<SeguridadSocial>(service.update(seguridadSocial, seguridadSocialId), HttpStatus.OK);
	}

}
