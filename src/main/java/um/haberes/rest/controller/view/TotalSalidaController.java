/**
 * 
 */
package um.haberes.rest.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import um.haberes.rest.exception.view.TotalSalidaNotFoundException;
import um.haberes.rest.kotlin.model.view.TotalSalida;
import um.haberes.rest.service.view.TotalSalidaService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/totalsalida")
public class TotalSalidaController {

	@Autowired
	private TotalSalidaService service;

	@GetMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<TotalSalida> findAByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		try {
			return new ResponseEntity<>(service.findByPeriodo(anho, mes), HttpStatus.OK);
		} catch (TotalSalidaNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
