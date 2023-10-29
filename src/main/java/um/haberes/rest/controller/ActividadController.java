/**
 * 
 */
package um.haberes.rest.controller;

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

import um.haberes.rest.exception.ActividadException;
import um.haberes.rest.kotlin.model.Actividad;
import um.haberes.rest.kotlin.model.view.ActividadPeriodo;
import um.haberes.rest.service.ActividadService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/actividad")
public class ActividadController {
	@Autowired
	private ActividadService service;

	@GetMapping("/legajo/{legajoId}")
	public ResponseEntity<List<Actividad>> findAllByLegajoId(@PathVariable Long legajoId) {
		return new ResponseEntity<>(service.findAllByLegajoId(legajoId), HttpStatus.OK);
	}

	@GetMapping("/unique/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Actividad> findByUnique(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		try {
			return new ResponseEntity<>(service.findByUnique(legajoId, anho, mes), HttpStatus.OK);
		} catch (ActividadException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/periodo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<ActividadPeriodo>> findAllByPeriodo(@PathVariable Long legajoId,
																   @PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findAllByPeriodo(legajoId, anho, mes), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<Actividad> add(@RequestBody Actividad actividad) {
		return new ResponseEntity<>(service.add(actividad), HttpStatus.OK);
	}

	@PutMapping("/{actividadId}")
	public ResponseEntity<Actividad> update(@RequestBody Actividad actividad, @PathVariable Long actividadId) {
		return new ResponseEntity<>(service.update(actividad, actividadId), HttpStatus.OK);
	}

}
