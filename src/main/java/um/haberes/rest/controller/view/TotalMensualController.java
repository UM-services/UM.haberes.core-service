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

import um.haberes.rest.model.view.TotalMensual;
import um.haberes.rest.service.view.TotalMensualService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/totalmensual")
public class TotalMensualController {

	@Autowired
	private TotalMensualService service;

	@GetMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<List<TotalMensual>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<TotalMensual>>(service.findAllByPeriodo(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/unique/{anho}/{mes}/{codigoId}")
	public ResponseEntity<TotalMensual> findByUnique(@PathVariable Integer anho, @PathVariable Integer mes,
			@PathVariable Integer codigoId) {
		return new ResponseEntity<TotalMensual>(service.findByUnique(anho, mes, codigoId), HttpStatus.OK);
	}

}
