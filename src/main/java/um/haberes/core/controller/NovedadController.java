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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import um.haberes.core.exception.NovedadException;
import um.haberes.core.kotlin.model.Novedad;
import um.haberes.core.service.NovedadService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/novedad")
@RequiredArgsConstructor
public class NovedadController {

	private final NovedadService service;

	@GetMapping("/legajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<Novedad>> findAllByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
														 @PathVariable Integer mes) {
        return ResponseEntity.ok(service.findAllByLegajo(legajoId, anho, mes));
	}

	@GetMapping("/codigo/{codigoId}/{anho}/{mes}")
	public ResponseEntity<List<Novedad>> findAllByCodigo(@PathVariable Integer codigoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
        return ResponseEntity.ok(service.findAllByCodigo(codigoId, anho, mes));
	}

	@GetMapping("/importado/{importado}/{anho}/{mes}")
	public ResponseEntity<List<Novedad>> findAllByImportado(@PathVariable Byte importado, @PathVariable Integer anho,
			@PathVariable Integer mes) {
        return ResponseEntity.ok(service.findAllByImportado(importado, anho, mes));
	}

	@GetMapping("/{novedadId}")
	public ResponseEntity<Novedad> findByNovedadId(@PathVariable Long novedadId) {
		try {
			return new ResponseEntity<Novedad>(service.findByNovedadId(novedadId), HttpStatus.OK);
		} catch (NovedadException e) {
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
            return ResponseEntity.ok(service.findByUnique(legajoId, anho, mes, codigoId, dependenciaIdLocal));
		} catch (NovedadException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<Novedad> add(@RequestBody Novedad novedad) {
        return ResponseEntity.ok(service.add(novedad));
	}

	@PutMapping("/{novedadId}")
	public ResponseEntity<Novedad> update(@RequestBody Novedad novedad, @PathVariable Long novedadId) {
        return ResponseEntity.ok(service.update(novedad, novedadId));
	}

	@DeleteMapping("/{novedadId}")
	public ResponseEntity<Void> deleteByNovedadId(@PathVariable Long novedadId) {
		service.deleteByNovedadId(novedadId);
        return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<Void> deleteAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		service.deleteAllByPeriodo(anho, mes);
        return ResponseEntity.noContent().build();
	}

}
