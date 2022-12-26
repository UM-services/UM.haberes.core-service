/**
 * 
 */
package ar.edu.um.haberes.rest.controller.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.um.haberes.rest.model.view.AsignadoCategoria;
import ar.edu.um.haberes.rest.service.view.AsignadoCategoriaService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/asignadocategoria")
public class AsignadoCategoriaController {

	@Autowired
	private AsignadoCategoriaService service;

	@GetMapping("/asignado/{dependenciaId}/{categoriaId}")
	public ResponseEntity<List<AsignadoCategoria>> findAllAsignados(@PathVariable Integer dependenciaId,
			@PathVariable Integer categoriaId) {
		return new ResponseEntity<List<AsignadoCategoria>>(service.findAllAsignados(dependenciaId, categoriaId),
				HttpStatus.OK);
	}

}
