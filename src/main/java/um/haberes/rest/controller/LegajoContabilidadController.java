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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.kotlin.model.LegajoContabilidad;
import um.haberes.rest.service.LegajoContabilidadService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/legajocontabilidad")
public class LegajoContabilidadController {
	
	@Autowired
	private LegajoContabilidadService service;

	@GetMapping("/diferencia/{anho}/{mes}")
	public ResponseEntity<List<LegajoContabilidad>> findAllDiferenciaByPeriodo(@PathVariable Integer anho,
																			   @PathVariable Integer mes) {
		return new ResponseEntity<List<LegajoContabilidad>>(service.findAllDiferenciaByPeriodo(anho, mes),
				HttpStatus.OK);
	}

	@GetMapping("/unique/{legajoId}/{anho}/{mes}")
	public @ResponseBody ResponseEntity<LegajoContabilidad> findByUnique(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<LegajoContabilidad>(service.findByUnique(legajoId, anho, mes), HttpStatus.OK);
	}

	@DeleteMapping("/{legajocontabilidadId}")
	public ResponseEntity<Void> delete(@PathVariable Long legajocontabilidadId) {
		service.delete(legajocontabilidadId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/")
	public ResponseEntity<LegajoContabilidad> add(@RequestBody LegajoContabilidad legajocontabilidad) {
		return new ResponseEntity<LegajoContabilidad>(service.add(legajocontabilidad), HttpStatus.OK);
	}

	@PutMapping("/{legajocontabilidadId}")
	public ResponseEntity<LegajoContabilidad> update(@RequestBody LegajoContabilidad legajocontabilidad,
			@PathVariable Long legajocontabilidadId) {
		return new ResponseEntity<LegajoContabilidad>(service.update(legajocontabilidad, legajocontabilidadId),
				HttpStatus.OK);
	}

}
