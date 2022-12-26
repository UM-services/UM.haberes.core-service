/**
 * 
 */
package ar.edu.um.haberes.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.um.haberes.rest.model.BonoImpresion;
import ar.edu.um.haberes.rest.service.BonoImpresionService;

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
		return new ResponseEntity<BonoImpresion>(service.add(bonoimpresion), HttpStatus.OK);
	}

}
