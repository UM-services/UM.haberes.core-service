/**
 * 
 */
package ar.edu.um.haberes.rest.controller;

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

import ar.edu.um.haberes.rest.exception.NovedadNotFoundException;
import ar.edu.um.haberes.rest.model.Novedad;
import ar.edu.um.haberes.rest.service.NovedadService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/novedad")
public class NovedadController {

	@Autowired
	private NovedadService service;

	@GetMapping("/legajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<Novedad>> findAllByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<Novedad>>(service.findAllByLegajo(legajoId, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/codigo/{codigoId}/{anho}/{mes}")
	public ResponseEntity<List<Novedad>> findAllByCodigo(@PathVariable Integer codigoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<Novedad>>(service.findAllByCodigo(codigoId, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/importado/{importado}/{anho}/{mes}")
	public ResponseEntity<List<Novedad>> findAllByImportado(@PathVariable Byte importado, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<Novedad>>(service.findAllByImportado(importado, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/{novedadId}")
	public ResponseEntity<Novedad> findByNovedadId(@PathVariable Long novedadId) {
		try {
			return new ResponseEntity<Novedad>(service.findByNovedadId(novedadId), HttpStatus.OK);
		} catch (NovedadNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/unique/{legajoId}/{anho}/{mes}/{codigoId}/{dependenciaId}")
	public ResponseEntity<Novedad> findByUnique(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer codigoId, @PathVariable String dependenciaId) {
		Integer dependenciaIdLocal = null;
		if (!dependenciaId.equals("null"))
			dependenciaIdLocal = Integer.valueOf(dependenciaId);
		try {
			return new ResponseEntity<Novedad>(service.findByUnique(legajoId, anho, mes, codigoId, dependenciaIdLocal),
					HttpStatus.OK);
		} catch (NovedadNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<Novedad> add(@RequestBody Novedad novedad) {
		return new ResponseEntity<Novedad>(service.add(novedad), HttpStatus.OK);
	}

	@PutMapping("/{novedadId}")
	public ResponseEntity<Novedad> update(@RequestBody Novedad novedad, @PathVariable Long novedadId) {
		return new ResponseEntity<Novedad>(service.update(novedad, novedadId), HttpStatus.OK);
	}

	@DeleteMapping("/{novedadId}")
	public ResponseEntity<Void> deleteByNovedadId(@PathVariable Long novedadId) {
		service.deleteByNovedadId(novedadId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<Void> deleteAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		service.deleteAllByPeriodo(anho, mes);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
