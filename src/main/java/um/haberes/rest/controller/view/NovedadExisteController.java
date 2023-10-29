/**
 * 
 */
package um.haberes.rest.controller.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.kotlin.model.view.NovedadExiste;
import um.haberes.rest.service.view.NovedadExisteService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/novedadExiste")
public class NovedadExisteController {
	
	@Autowired
	private NovedadExisteService service;

	@GetMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<List<NovedadExiste>> findAllByPeriodo(@PathVariable Integer anho,
																@PathVariable Integer mes) {
		return new ResponseEntity<List<NovedadExiste>>(service.findAllByPeriodo(anho, mes), HttpStatus.OK);
	}

}
