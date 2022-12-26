/**
 * 
 */
package ar.edu.um.haberes.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.um.haberes.rest.model.DesignacionTipo;
import ar.edu.um.haberes.rest.service.DesignacionTipoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/designaciontipo")
public class DesignacionTipoController {

	@Autowired
	private DesignacionTipoService service;

	@GetMapping("/")
	public ResponseEntity<List<DesignacionTipo>> findAll() {
		return new ResponseEntity<List<DesignacionTipo>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{designacionTipoId}")
	public ResponseEntity<DesignacionTipo> findByDesignacionTipoId(@PathVariable Integer designacionTipoId) {
		return new ResponseEntity<DesignacionTipo>(service.findByDesignacionTipoId(designacionTipoId), HttpStatus.OK);
	}
}
