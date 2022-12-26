/**
 * 
 */
package ar.edu.um.haberes.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.um.haberes.rest.model.ConversionLog;
import ar.edu.um.haberes.rest.service.ConversionLogService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping(value = "/conversionlog")
public class ConversionLogController {
	
	@Autowired
	private ConversionLogService service;
	
	@GetMapping("/")
	public ResponseEntity<List<ConversionLog>> findAll() {
		return new ResponseEntity<List<ConversionLog>>(service.findAll(), HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<ConversionLog> add(@RequestBody ConversionLog conversion) {
		return new ResponseEntity<ConversionLog>(service.add(conversion), HttpStatus.OK);
	}
	
}
