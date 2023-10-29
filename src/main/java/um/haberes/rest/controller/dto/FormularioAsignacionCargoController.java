/**
 * 
 */
package um.haberes.rest.controller.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.kotlin.model.dto.FormularioAsignacionCargo;
import um.haberes.rest.service.dto.FormularioAsignacionCargoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/formularioAsignacionCargo")
public class FormularioAsignacionCargoController {

	@Autowired
	private FormularioAsignacionCargoService service;

	@GetMapping("/")
	public ResponseEntity<FormularioAsignacionCargo> findData() {
		return new ResponseEntity<>(service.findData(), HttpStatus.OK);
	}
	
}
