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

import um.haberes.rest.kotlin.model.CodigoImputacion;
import um.haberes.rest.service.CodigoImputacionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/codigoimputacion")
public class CodigoImputacionController {

	@Autowired
	private CodigoImputacionService service;

	@GetMapping("/")
	public ResponseEntity<List<CodigoImputacion>> findAll() {
		return new ResponseEntity<List<CodigoImputacion>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{codigoimputacionId}")
	public ResponseEntity<CodigoImputacion> findByCodigoimputacionId(@PathVariable Long codigoimputacionId) {
		return new ResponseEntity<CodigoImputacion>(service.findByCodigoimputacionId(codigoimputacionId),
				HttpStatus.OK);
	}

	@GetMapping("/unique/{dependenciaId}/{facultadId}/{geograficaId}/{codigoId}")
	public ResponseEntity<CodigoImputacion> findByUnique(@PathVariable Integer dependenciaId,
			@PathVariable Integer facultadId, @PathVariable Integer geograficaId, @PathVariable Integer codigoId) {
		return new ResponseEntity<CodigoImputacion>(
				service.findByUnique(dependenciaId, facultadId, geograficaId, codigoId), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<CodigoImputacion> add(@RequestBody CodigoImputacion codigoimputacion) {
		return new ResponseEntity<CodigoImputacion>(service.add(codigoimputacion), HttpStatus.OK);
	}

	@PutMapping("/{codigoimputacionId}")
	public ResponseEntity<CodigoImputacion> update(@RequestBody CodigoImputacion codigoimputacion,
			@PathVariable Long codigoimputacionId) {
		return new ResponseEntity<CodigoImputacion>(service.update(codigoimputacion, codigoimputacionId),
				HttpStatus.OK);
	}

}
