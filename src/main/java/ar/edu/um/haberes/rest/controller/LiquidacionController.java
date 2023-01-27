/**
 * 
 */
package ar.edu.um.haberes.rest.controller;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
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

import ar.edu.um.haberes.rest.exception.LiquidacionException;
import ar.edu.um.haberes.rest.model.Liquidacion;
import ar.edu.um.haberes.rest.model.view.LiquidacionPeriodo;
import ar.edu.um.haberes.rest.service.LiquidacionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/liquidacion")
public class LiquidacionController {

	@Autowired
	private LiquidacionService service;

	@GetMapping("/periodo/{anho}/{mes}/{limit}")
	public ResponseEntity<List<Liquidacion>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes,
			@PathVariable Integer limit) {
		return new ResponseEntity<List<Liquidacion>>(service.findAllByPeriodo(anho, mes, limit), HttpStatus.OK);
	}

	@GetMapping("/periodolegajo/{anho}/{mes}/{legajoId}/{limit}")
	public ResponseEntity<List<Liquidacion>> findAllByPeriodoLegajo(@PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Long legajoId, @PathVariable Integer limit) {
		return new ResponseEntity<List<Liquidacion>>(service.findAllByPeriodoLegajo(anho, mes, legajoId, limit),
				HttpStatus.OK);
	}

	@GetMapping("/semestre/{anho}/{semestre}/{limit}")
	public ResponseEntity<List<Liquidacion>> findAllBySemestre(@PathVariable Integer anho,
			@PathVariable Integer semestre, @PathVariable Integer limit) {
		return new ResponseEntity<List<Liquidacion>>(service.findAllBySemestre(anho, semestre, limit), HttpStatus.OK);
	}

	@GetMapping("/semestrelegajo/{anho}/{mes}/{legajoId}/{limit}")
	public ResponseEntity<List<Liquidacion>> findAllBySemestreLegajo(@PathVariable Integer anho,
			@PathVariable Integer semestre, @PathVariable Long legajoId, @PathVariable Integer limit) {
		return new ResponseEntity<List<Liquidacion>>(service.findAllBySemestreLegajo(anho, semestre, legajoId, limit),
				HttpStatus.OK);
	}

	@GetMapping("/legajo/{legajoId}")
	public ResponseEntity<List<Liquidacion>> findAllByLegajo(@PathVariable Long legajoId) {
		return new ResponseEntity<List<Liquidacion>>(service.findAllByLegajo(legajoId), HttpStatus.OK);
	}

	@GetMapping("/legajoforward/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<LiquidacionPeriodo>> findAllByLegajoForward(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<LiquidacionPeriodo>>(service.findAllByLegajoForward(legajoId, anho, mes),
				HttpStatus.OK);
	}

	@GetMapping("/dependencia/{dependenciaId}/{anho}/{mes}/{salida}")
	public ResponseEntity<List<Liquidacion>> findAllByDependencia(@PathVariable Integer dependenciaId,
			@PathVariable Integer anho, @PathVariable Integer mes, @PathVariable String salida) {
		return new ResponseEntity<List<Liquidacion>>(service.findAllByDependencia(dependenciaId, anho, mes, salida),
				HttpStatus.OK);
	}

	@GetMapping("/acreditado/{anho}/{mes}")
	public ResponseEntity<List<Liquidacion>> findAllByAcreditado(@PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<Liquidacion>>(service.findAllByAcreditado(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/{liquidacionId}")
	public ResponseEntity<Liquidacion> findByLiquidacionId(@PathVariable Long liquidacionId) {
		try {
			return new ResponseEntity<Liquidacion>(service.findByLiquidacionId(liquidacionId), HttpStatus.OK);
		} catch (LiquidacionException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/unique/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Liquidacion> findByUnique(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		try {
			return new ResponseEntity<Liquidacion>(service.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes),
					HttpStatus.OK);
		} catch (LiquidacionException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<Liquidacion> add(@RequestBody Liquidacion liquidacion) {
		return new ResponseEntity<Liquidacion>(service.add(liquidacion), HttpStatus.OK);
	}

	@PostMapping("/version/{version}")
	public ResponseEntity<Liquidacion> addVersion(@RequestBody Liquidacion liquidacion, @PathVariable Integer version) {
		return new ResponseEntity<Liquidacion>(service.addVersion(liquidacion, version), HttpStatus.OK);
	}

	@PostMapping("/acreditado/{fecha}")
	public ResponseEntity<Liquidacion> acreditado(@RequestBody Liquidacion liquidacion,
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fecha) {
		return new ResponseEntity<Liquidacion>(service.acreditado(liquidacion, fecha), HttpStatus.OK);
	}

	@PutMapping("/{liquidacionId}")
	public ResponseEntity<Liquidacion> update(@RequestBody Liquidacion liquidacion, @PathVariable Long liquidacionId) {
		return new ResponseEntity<Liquidacion>(service.update(liquidacion, liquidacionId), HttpStatus.OK);
	}

	@PutMapping("/saveall/{version}")
	public ResponseEntity<List<Liquidacion>> saveall(@RequestBody List<Liquidacion> liquidaciones,
			@PathVariable Integer version) {
		return new ResponseEntity<List<Liquidacion>>(service.saveall(liquidaciones, version), HttpStatus.OK);
	}

	@PutMapping("/version/{liquidacionId}/{version}")
	public ResponseEntity<Liquidacion> updateVersion(@RequestBody Liquidacion liquidacion,
			@PathVariable Long liquidacionId, @PathVariable Integer version) {
		return new ResponseEntity<Liquidacion>(service.updateVersion(liquidacion, liquidacionId, version),
				HttpStatus.OK);
	}

	@DeleteMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<Void> deleteByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		service.deleteByPeriodo(anho, mes);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
