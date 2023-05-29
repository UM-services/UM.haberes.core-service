/**
 * 
 */
package um.haberes.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.model.LegajoCodigoImputacion;
import um.haberes.rest.service.LegajoCodigoImputacionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/legajocodigoimputacion")
public class LegajoCodigoImputacionController {

	@Autowired
	private LegajoCodigoImputacionService service;

	@GetMapping("/legajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<LegajoCodigoImputacion>> findAllByLegajo(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<LegajoCodigoImputacion>>(service.findAllByLegajo(legajoId, anho, mes),
				HttpStatus.OK);
	}

}
