/**
 * 
 */
package ar.edu.um.haberes.rest.controller.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.um.haberes.rest.model.view.ImputadoSueldo;
import ar.edu.um.haberes.rest.service.view.ImputadoSueldoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/imputadosueldo")
public class ImputadoSueldoController {
	
	@Autowired
	private ImputadoSueldoService service;
	
	@GetMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<List<ImputadoSueldo>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<ImputadoSueldo>>(service.findAllByPeriodo(anho, mes), HttpStatus.OK);
	}

}
