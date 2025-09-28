/**
 * 
 */
package um.haberes.core.controller;

import lombok.RequiredArgsConstructor;
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

import um.haberes.core.exception.SeguridadSocialException;
import um.haberes.core.kotlin.model.SeguridadSocial;
import um.haberes.core.service.SeguridadSocialService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/seguridadSocial")
@RequiredArgsConstructor
public class SeguridadSocialController {

	private final SeguridadSocialService service;

	@GetMapping("/unique/{anho}/{mes}")
	public ResponseEntity<SeguridadSocial> findByUnique(@PathVariable Integer anho, @PathVariable Integer mes) {
		try {
            return ResponseEntity.ok(service.findByUnique(anho, mes));
		} catch (SeguridadSocialException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<SeguridadSocial> add(@RequestBody SeguridadSocial seguridadSocial) {
        return ResponseEntity.ok(service.add(seguridadSocial));
	}

	@PutMapping("/{seguridadSocialId}")
	public ResponseEntity<SeguridadSocial> update(@RequestBody SeguridadSocial seguridadSocial,
			@PathVariable Long seguridadSocialId) {
        return ResponseEntity.ok(service.update(seguridadSocial, seguridadSocialId));
	}

}
