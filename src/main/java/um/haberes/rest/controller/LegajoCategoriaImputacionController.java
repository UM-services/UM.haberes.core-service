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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.kotlin.model.LegajoCategoriaImputacion;
import um.haberes.rest.service.LegajoCategoriaImputacionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/legajocategoriaimputacion")
public class LegajoCategoriaImputacionController {

	@Autowired
	private LegajoCategoriaImputacionService service;

	@GetMapping("/legajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<LegajoCategoriaImputacion>> findAllByLegajo(@PathVariable Long legajoId,
																		   @PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<LegajoCategoriaImputacion>>(service.findAllByLegajo(legajoId, anho, mes),
				HttpStatus.OK);
	}

}
