/**
 * 
 */
package um.haberes.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.kotlin.model.BonoImpresion;
import um.haberes.core.service.BonoImpresionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/bonoimpresion")
public class BonoImpresionController {

	private final BonoImpresionService service;

	public BonoImpresionController(BonoImpresionService service) {
		this.service = service;
	}

	@PostMapping("/")
	public ResponseEntity<BonoImpresion> add(@RequestBody BonoImpresion bonoimpresion) {
		return new ResponseEntity<>(service.add(bonoimpresion), HttpStatus.OK);
	}

}
