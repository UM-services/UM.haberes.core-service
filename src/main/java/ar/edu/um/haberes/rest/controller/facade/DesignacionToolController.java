/**
 * 
 */
package ar.edu.um.haberes.rest.controller.facade;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.um.haberes.rest.service.facade.DesignacionToolService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/designaciontool")
public class DesignacionToolController {

	@Autowired
	private DesignacionToolService service;

	@GetMapping("/convertirbylegajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Void> convertirGradoByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		Boolean aplicaExcepcion = false;
		if ((long) anho * 100L + mes < 201904)
			aplicaExcepcion = true;
		service.convertirGradoByLegajo(legajoId, anho, mes, aplicaExcepcion);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/redesignarbylegajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Void> redesignarGradoByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		Boolean aplicaExcepcion = false;
		if ((long) anho * 100L + mes < 201904)
			aplicaExcepcion = true;
		service.redesignarGradoByLegajo(legajoId, anho, mes, aplicaExcepcion);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/fusionarbylegajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Void> fusionarGradoByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		Boolean aplicaExcepcion = false;
		if ((long) anho * 100L + mes < 201904)
			aplicaExcepcion = true;
		service.fusionarGradoByLegajo(legajoId, anho, mes, null, aplicaExcepcion);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/desarraigobylegajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Void> desarraigoGradobylegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		service.desarraigoGradoByLegajo(legajoId, anho, mes);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/duplicarbylegajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Void> duplicarByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		service.duplicarByLegajo(legajoId, anho, mes);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/deletezombies/{anho}/{mes}")
	public ResponseEntity<Void> deleteZombies(@PathVariable Integer anho, @PathVariable Integer mes) {
		service.deleteZombies(anho, mes);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

	}

	@GetMapping("/indiceantiguedad/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<BigDecimal>> indiceantiguedad(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<BigDecimal>>(service.indiceAntiguedad(legajoId, anho, mes), HttpStatus.OK);
	}
}
