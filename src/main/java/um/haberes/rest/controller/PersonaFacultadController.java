/**
 * 
 */
package um.haberes.rest.controller;

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

import um.haberes.rest.model.PersonaFacultad;
import um.haberes.rest.service.PersonaFacultadService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/personaFacultad")
public class PersonaFacultadController {

	@Autowired
	private PersonaFacultadService service;

	@GetMapping("/facultad/{facultadId}")
	public ResponseEntity<List<PersonaFacultad>> findAllByFacultad(@PathVariable Integer facultadId) {
		return new ResponseEntity<List<PersonaFacultad>>(service.findAllByFacultad(facultadId), HttpStatus.OK);
	}

	@GetMapping("/persona/{legajoId}")
	public ResponseEntity<List<PersonaFacultad>> findAllByPersona(@PathVariable Long legajoId) {
		return new ResponseEntity<List<PersonaFacultad>>(service.findAllByPersona(legajoId), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<PersonaFacultad> add(@RequestBody PersonaFacultad personaFacultad) {
		return new ResponseEntity<PersonaFacultad>(service.add(personaFacultad), HttpStatus.OK);
	}

	@DeleteMapping("/unique/{legajoId}/{facultadId}")
	public ResponseEntity<Void> deleteByUnique(@PathVariable Long legajoId, @PathVariable Integer facultadId) {
		service.deleteByUnique(legajoId, facultadId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
