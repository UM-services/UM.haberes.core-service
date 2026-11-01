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

import um.haberes.core.model.BonoImpresionEntity;
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
	public ResponseEntity<BonoImpresionEntity> add(@RequestBody BonoImpresionEntity bonoimpresion) {
		return new ResponseEntity<>(service.add(bonoimpresion), HttpStatus.OK);
	}

}
