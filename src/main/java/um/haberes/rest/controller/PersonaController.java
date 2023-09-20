/**
 * 
 */
package um.haberes.rest.controller;

import java.math.BigDecimal;
import java.util.List;

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

import um.haberes.rest.exception.PersonaException;
import um.haberes.rest.kotlin.view.PersonaSearch;
import um.haberes.rest.model.Persona;
import um.haberes.rest.service.PersonaService;
import um.haberes.rest.util.transfer.FileInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/persona")
@Slf4j
public class PersonaController {

	@Autowired
	private PersonaService service;

	@GetMapping("/")
	public ResponseEntity<List<Persona>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@PostMapping("/legajos")
	public ResponseEntity<List<Persona>> findAllLegajos(@RequestBody List<Long> legajos) {
		return new ResponseEntity<>(service.findAllLegajos(legajos), HttpStatus.OK);
	}

	// Legajos que tienen cargo en cursos para el período solicitado
	@GetMapping("/docente/{anho}/{mes}")
	public ResponseEntity<List<Persona>> findAllDocente(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findAllDocente(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/nodocente/{anho}/{mes}")
	public ResponseEntity<List<Persona>> findAllNoDocente(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findAllNoDocente(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/semestre/{anho}/{semestre}")
	public ResponseEntity<List<Persona>> findAllBySemestre(@PathVariable Integer anho, @PathVariable Integer semestre) {
		return new ResponseEntity<>(service.findAllBySemestre(anho, semestre), HttpStatus.OK);
	}

	@GetMapping("/desarraigo/{anho}/{mes}")
	public ResponseEntity<List<Persona>> findAllByDesarraigo(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findAllByDesarraigo(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/liquidables")
	public ResponseEntity<List<Persona>> findAllLiquidables() {
		return new ResponseEntity<>(service.findAllLiquidables(), HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<List<PersonaSearch>> findByStrings(@RequestBody List<String> conditions) {
		return new ResponseEntity<>(service.findByStrings(conditions), HttpStatus.OK);
	}

	@GetMapping("/{legajoId}")
	public ResponseEntity<Persona> findByLegajoId(@PathVariable Long legajoId) {
		try {
			return new ResponseEntity<>(service.findByLegajoId(legajoId), HttpStatus.OK);
		} catch (PersonaException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/documento/{documento}")
	public ResponseEntity<Persona> findByDocumento(@PathVariable BigDecimal documento) {
		try {
			return new ResponseEntity<>(service.findByDocumento(documento), HttpStatus.OK);
		} catch (PersonaException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<Persona> add(@RequestBody Persona persona) {
		return new ResponseEntity<>(service.add(persona), HttpStatus.OK);
	}

	@PutMapping("/{legajoId}")
	public ResponseEntity<Persona> update(@RequestBody Persona persona, @PathVariable Long legajoId) {
		return new ResponseEntity<>(service.update(persona, legajoId), HttpStatus.OK);
	}

	@PutMapping("/")
	public ResponseEntity<List<Persona>> saveall(@RequestBody List<Persona> personas) {
		return new ResponseEntity<>(service.saveall(personas), HttpStatus.OK);
	}

	@PostMapping("/upload")
	public ResponseEntity<List<Persona>> upload(@RequestBody FileInfo fileinfo) {
		log.debug("Upload - Controller");
		return new ResponseEntity<>(service.upload(fileinfo), HttpStatus.OK);
	}

}
