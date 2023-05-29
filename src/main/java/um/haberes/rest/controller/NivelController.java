/**
 * 
 */
package um.haberes.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.model.Nivel;
import um.haberes.rest.service.NivelService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/nivel")
public class NivelController {
	
	@Autowired
	private NivelService service;
	
	@GetMapping("/")
	public ResponseEntity<List<Nivel>> findAll() {
		return new ResponseEntity<List<Nivel>>(service.findAll(), HttpStatus.OK);
	}
	
}
