/**
 * 
 */
package um.haberes.core.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.kotlin.model.DesignacionTipo;
import um.haberes.core.service.DesignacionTipoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/designaciontipo")
public class DesignacionTipoController {

	private final DesignacionTipoService service;

	public DesignacionTipoController(DesignacionTipoService service) {
		this.service = service;
	}

	@GetMapping("/")
	public ResponseEntity<List<DesignacionTipo>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{designacionTipoId}")
	public ResponseEntity<DesignacionTipo> findByDesignacionTipoId(@PathVariable Integer designacionTipoId) {
		return new ResponseEntity<>(service.findByDesignacionTipoId(designacionTipoId), HttpStatus.OK);
	}
}
