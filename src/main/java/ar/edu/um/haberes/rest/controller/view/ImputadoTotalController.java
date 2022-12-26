/**
 * 
 */
package ar.edu.um.haberes.rest.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.um.haberes.rest.model.view.ImputadoTotal;
import ar.edu.um.haberes.rest.service.view.ImputadoTotalService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/imputadototal")
public class ImputadoTotalController {
	
	@Autowired
	private ImputadoTotalService service;
	
	@GetMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<ImputadoTotal> findByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<ImputadoTotal>(service.findByPeriodo(anho, mes), HttpStatus.OK);
	}
	
}
