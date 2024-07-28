/**
 * 
 */
package um.haberes.core.controller.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.kotlin.model.dto.FormularioAsignacionCargo;
import um.haberes.core.service.dto.FormularioAsignacionCargoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/formularioAsignacionCargo")
public class FormularioAsignacionCargoController {

	@Autowired
	private FormularioAsignacionCargoService service;

	@GetMapping("/")
	public ResponseEntity<FormularioAsignacionCargo> findData() {
		return new ResponseEntity<>(service.findData(), HttpStatus.OK);
	}
	
}
