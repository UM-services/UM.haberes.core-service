/**
 * 
 */
package um.haberes.core.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.model.LegajoControlEntity;
import um.haberes.core.service.LegajoControlService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/legajocontrol")
public class LegajoControlController {

	private final LegajoControlService service;

	public LegajoControlController(LegajoControlService service) {
		this.service = service;
	}

	@GetMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<List<LegajoControlEntity>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findAllByPeriodo(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/liquidado/{anho}/{mes}")
	public ResponseEntity<List<LegajoControlEntity>> findAllLiquidadoByPeriodo(@PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<>(service.findAllLiquidadoByPeriodo(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/dependencia/{anho}/{mes}/{dependenciaId}/{filtro}")
	public ResponseEntity<List<LegajoControlEntity>> findAllDependenciaByPeriodo(@PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer dependenciaId, @PathVariable String filtro) {
		return new ResponseEntity<>(
				service.findAllDependenciaByPeriodo(anho, mes, dependenciaId, filtro), HttpStatus.OK);
	}

	@GetMapping("/unique/{legajoId}/{anho}/{mes}")
	public ResponseEntity<LegajoControlEntity> findByUnique(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<>(service.findByUnique(legajoId, anho, mes), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<LegajoControlEntity> add(@RequestBody LegajoControlEntity legajocontrol) {
		return new ResponseEntity<>(service.add(legajocontrol), HttpStatus.OK);
	}

	@PutMapping("/{legajocontrolId}")
	public ResponseEntity<LegajoControlEntity> update(@RequestBody LegajoControlEntity legajocontrol,
			@PathVariable Long legajocontrolId) {
		return new ResponseEntity<>(service.update(legajocontrol, legajocontrolId), HttpStatus.OK);
	}

	@PutMapping("/saveall")
	public ResponseEntity<List<LegajoControlEntity>> saveAll(@RequestBody List<LegajoControlEntity> controles) {
		return new ResponseEntity<>(service.saveAll(controles), HttpStatus.OK);
	}

}
