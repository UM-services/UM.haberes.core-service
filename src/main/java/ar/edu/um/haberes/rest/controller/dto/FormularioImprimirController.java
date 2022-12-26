/**
 * 
 */
package ar.edu.um.haberes.rest.controller.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.um.haberes.rest.model.dto.FormularioImprimir;
import ar.edu.um.haberes.rest.service.dto.FormularioImprimirService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/formularioImprimir")
public class FormularioImprimirController {

	@Autowired
	private FormularioImprimirService service;

	@GetMapping("/{anho}/{mes}/{dependenciaId}/{filtro}")
	public ResponseEntity<FormularioImprimir> findData(@PathVariable Integer anho, @PathVariable Integer mes,
			@PathVariable Integer dependenciaId, @PathVariable String filtro) {
		return new ResponseEntity<FormularioImprimir>(service.findData(anho, mes, dependenciaId, filtro),
				HttpStatus.OK);
	}

}
