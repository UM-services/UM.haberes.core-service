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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import um.haberes.rest.exception.LegajoBancoException;
import um.haberes.rest.model.LegajoBanco;
import um.haberes.rest.service.LegajoBancoService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/legajobanco")
@Slf4j
public class LegajoBancoController {

	@Autowired
	private LegajoBancoService service;

	@GetMapping("/{legajoBancoId}")
	public ResponseEntity<LegajoBanco> findByLegajoBancoId(@PathVariable Long legajoBancoId) {
		try {
			return new ResponseEntity<LegajoBanco>(service.findByLegajoBancoId(legajoBancoId), HttpStatus.OK);
		} catch (LegajoBancoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/unique/{legajoId}/{anho}/{mes}/{cbu}")
	public ResponseEntity<LegajoBanco> findByUnique(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable String cbu) {
		try {
			return new ResponseEntity<LegajoBanco>(service.findByUnique(legajoId, anho, mes, cbu), HttpStatus.OK);
		} catch (LegajoBancoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/lastlegajo/{legajoId}")
	public ResponseEntity<LegajoBanco> findLastByLegajoId(@PathVariable Long legajoId) {
		try {
			return new ResponseEntity<LegajoBanco>(service.findLastByLegajoId(legajoId), HttpStatus.OK);
		} catch (LegajoBancoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/legajo/{legajoId}")
	public ResponseEntity<List<LegajoBanco>> findAllByLegajoId(@PathVariable Long legajoId) {
		return new ResponseEntity<List<LegajoBanco>>(service.findAllByLegajoId(legajoId), HttpStatus.OK);
	}

	@GetMapping("/legajoperiodo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<LegajoBanco>> findAllByLegajoPeriodo(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		log.debug(String.format("Search %d/%d/%d", legajoId, anho, mes));
		return new ResponseEntity<List<LegajoBanco>>(service.findAllByLegajoPeriodo(legajoId, anho, mes),
				HttpStatus.OK);
	}

	@GetMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<List<LegajoBanco>> findAllPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<LegajoBanco>>(service.findAllPeriodo(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/periodosantander/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<LegajoBanco>> findAllPeriodoSantander(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<LegajoBanco>>(service.findAllPeriodoSantander(legajoId, anho, mes),
				HttpStatus.OK);
	}

	@GetMapping("/periodootrosbancos/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<LegajoBanco>> findAllPeriodoOtrosBancos(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<LegajoBanco>>(service.findAllPeriodoOtrosBancos(legajoId, anho, mes),
				HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<LegajoBanco> add(@RequestBody LegajoBanco legajobanco) {
		return new ResponseEntity<LegajoBanco>(service.add(legajobanco), HttpStatus.OK);
	}

	@PutMapping("/{legajobancoId}")
	public ResponseEntity<LegajoBanco> update(@RequestBody LegajoBanco legajobanco, @PathVariable Long legajobancoId) {
		return new ResponseEntity<LegajoBanco>(service.update(legajobanco, legajobancoId), HttpStatus.OK);
	}

	@GetMapping("/santander/{salida}/{anho}/{mes}/{dependenciaId}")
	public ResponseEntity<List<LegajoBanco>> findAllSantander(@PathVariable String salida, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer dependenciaId) {
		return new ResponseEntity<List<LegajoBanco>>(service.findAllSantander(salida, anho, mes, dependenciaId),
				HttpStatus.OK);
	}

	@GetMapping("/otrosbancos/{salida}/{anho}/{mes}/{dependenciaId}")
	public ResponseEntity<List<LegajoBanco>> findAllOtrosBancos(@PathVariable String salida, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer dependenciaId) {
		return new ResponseEntity<List<LegajoBanco>>(service.findAllOtrosBancos(salida, anho, mes, dependenciaId),
				HttpStatus.OK);
	}

	@DeleteMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<Void> deleteAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		service.deleteAllByPeriodo(anho, mes);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{legajobancoId}")
	public ResponseEntity<Void> delete(@PathVariable Long legajobancoId) {
		service.deleteByLegajobancoId(legajobancoId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
