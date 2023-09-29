/**
 * 
 */
package um.haberes.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.kotlin.model.BonoImpresion;
import um.haberes.rest.service.BonoImpresionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/bonoimpresion")
public class BonoImpresionController {

	@Autowired
	private BonoImpresionService service;

	@PostMapping("/")
	public ResponseEntity<BonoImpresion> add(@RequestBody BonoImpresion bonoimpresion) {
		return new ResponseEntity<>(service.add(bonoimpresion), HttpStatus.OK);
	}

}
