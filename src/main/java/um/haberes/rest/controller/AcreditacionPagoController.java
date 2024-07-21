/**
 * 
 */
package um.haberes.rest.controller;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.kotlin.model.AcreditacionPago;
import um.haberes.rest.service.AcreditacionPagoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/acreditacionpago")
public class AcreditacionPagoController {
	@Autowired
	private AcreditacionPagoService service;

	@GetMapping("/unique/{anho}/{mes}/{fechapago}")
	public ResponseEntity<AcreditacionPago> findByUnique(@PathVariable Integer anho, @PathVariable Integer mes,
														 @PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fechapago) {
		return new ResponseEntity<AcreditacionPago>(service.findByUnique(anho, mes, fechapago), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<AcreditacionPago> add(@RequestBody AcreditacionPago acreditacionpago) {
		return new ResponseEntity<AcreditacionPago>(service.add(acreditacionpago), HttpStatus.OK);
	}

	@PutMapping("/{acreditacionpagoId}")
	public ResponseEntity<AcreditacionPago> update(@RequestBody AcreditacionPago acreditacionpago,
			@PathVariable Long acreditacionpagoId) {
		return new ResponseEntity<AcreditacionPago>(service.update(acreditacionpago, acreditacionpagoId),
				HttpStatus.OK);
	}

}
