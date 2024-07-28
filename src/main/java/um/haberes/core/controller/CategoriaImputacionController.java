/**
 * 
 */
package um.haberes.core.controller;

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

import um.haberes.core.kotlin.model.CategoriaImputacion;
import um.haberes.core.service.CategoriaImputacionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/categoriaimputacion")
public class CategoriaImputacionController {

	@Autowired
	private CategoriaImputacionService service;

	@GetMapping("/")
	public ResponseEntity<List<CategoriaImputacion>> findAll() {
		return new ResponseEntity<List<CategoriaImputacion>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{categoriaimputacionId}")
	public ResponseEntity<CategoriaImputacion> findByCategoriaimputacionId(@PathVariable Long categoriaimputacionId) {
		return new ResponseEntity<CategoriaImputacion>(service.findByCategoriaimputacionId(categoriaimputacionId),
				HttpStatus.OK);
	}

	@GetMapping("/unique/{dependenciaId}/{facultadId}/{geograficaId}/{categoriaId}")
	public ResponseEntity<CategoriaImputacion> findByUnique(@PathVariable Integer dependenciaId,
			@PathVariable Integer facultadId, @PathVariable Integer geograficaId, @PathVariable Integer categoriaId) {
		return new ResponseEntity<CategoriaImputacion>(
				service.findByUnique(dependenciaId, facultadId, geograficaId, categoriaId), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<CategoriaImputacion> add(@RequestBody CategoriaImputacion categoriaimputacion) {
		return new ResponseEntity<CategoriaImputacion>(service.add(categoriaimputacion), HttpStatus.OK);
	}

	@PutMapping("/{categoriaimputacionId}")
	public ResponseEntity<CategoriaImputacion> update(@RequestBody CategoriaImputacion categoriaimputacion,
			@PathVariable Long categoriaimputacionId) {
		return new ResponseEntity<CategoriaImputacion>(service.update(categoriaimputacion, categoriaimputacionId),
				HttpStatus.OK);
	}

}
