/**
 * 
 */
package ar.edu.um.haberes.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.um.haberes.rest.model.Situacion;
import ar.edu.um.haberes.rest.service.SituacionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping(value = "/situacion")
public class SituacionController {
	
	@Autowired
	private SituacionService service;

	@GetMapping("/")
	public ResponseEntity<List<Situacion>> findAll() {
		return new ResponseEntity<List<Situacion>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{situacionID}")
	public ResponseEntity<Situacion> findBySituacionID(@PathVariable Integer situacionID) {
		return new ResponseEntity<Situacion>(service.findBySituacionID(situacionID), HttpStatus.OK);
	}
}
