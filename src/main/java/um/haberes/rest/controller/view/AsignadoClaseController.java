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

import um.haberes.rest.kotlin.view.AsignadoClase;
import um.haberes.rest.service.view.AsignadoClaseService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/asignadoclase")
public class AsignadoClaseController {

	@Autowired
	private AsignadoClaseService service;

	@GetMapping("/asignado/{dependenciaId}/{cargoclaseId}")
	public ResponseEntity<List<AsignadoClase>> findAllAsignados(@PathVariable Integer dependenciaId,
																@PathVariable Long cargoclaseId) {
		return new ResponseEntity<>(service.findAllAsignados(dependenciaId, cargoclaseId),
				HttpStatus.OK);
	}

}
