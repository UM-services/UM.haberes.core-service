/**
 * 
 */
package um.haberes.core.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
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

import um.haberes.core.kotlin.model.PersonaFacultad;
import um.haberes.core.service.PersonaFacultadService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/personaFacultad")
@RequiredArgsConstructor
public class PersonaFacultadController {

	private final PersonaFacultadService service;

	@GetMapping("/facultad/{facultadId}")
	public ResponseEntity<List<PersonaFacultad>> findAllByFacultad(@PathVariable Integer facultadId) {
        return ResponseEntity.ok(service.findAllByFacultad(facultadId));
	}

	@GetMapping("/persona/{legajoId}")
	public ResponseEntity<List<PersonaFacultad>> findAllByPersona(@PathVariable Long legajoId) {
        return ResponseEntity.ok(service.findAllByPersona(legajoId));
	}

	@PostMapping("/")
	public ResponseEntity<PersonaFacultad> add(@RequestBody PersonaFacultad personaFacultad) {
        return ResponseEntity.ok(service.add(personaFacultad));
	}

	@DeleteMapping("/unique/{legajoId}/{facultadId}")
	public ResponseEntity<Void> deleteByUnique(@PathVariable Long legajoId, @PathVariable Integer facultadId) {
		service.deleteByUnique(legajoId, facultadId);
        return ResponseEntity.noContent().build();
	}

}
