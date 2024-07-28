/**
 * 
 */
package um.haberes.core.controller.facade;

import um.haberes.core.kotlin.model.extern.CuentaMovimientoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.service.facade.ContableService;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/contable")
public class ContableController {

	private final ContableService service;

	@Autowired
	public ContableController(ContableService service) {
		this.service = service;
	}

	@GetMapping("/generatelegajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Void> generateByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		service.generateByLegajo(legajoId, anho, mes);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/deletelegajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<Void> deleteAllByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		service.deleteAllByLegajo(legajoId, anho, mes);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/deleteperiodo/{anho}/{mes}")
	public ResponseEntity<Void> deleteAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		service.deleteAllByPeriodo(anho, mes);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/asiento/{fechaContable}/{ordenContable}")
	public ResponseEntity<List<CuentaMovimientoDto>> findAllByAsiento(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fechaContable, @PathVariable Integer ordenContable) {
		return new ResponseEntity<>(service.findAllByAsiento(fechaContable, ordenContable), HttpStatus.OK);
	}

}
