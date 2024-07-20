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

import um.haberes.rest.kotlin.model.Dependencia;
import um.haberes.rest.service.DependenciaService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/dependencia")
public class DependenciaController {
	
	@Autowired
	private DependenciaService service;

	@GetMapping("/")
	public ResponseEntity<List<Dependencia>> findAll() {
		return new ResponseEntity<List<Dependencia>>(service.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/context/{facultadId}/{geograficaId}")
	public ResponseEntity<List<Dependencia>> findAllByFacultadIdAndGeograficaId(@PathVariable Integer facultadId, @PathVariable Integer geograficaId) {
		return new ResponseEntity<List<Dependencia>>(service.findAllByFacultadIdAndGeograficaId(facultadId, geograficaId), HttpStatus.OK);
	}
	
	@GetMapping("/{dependenciaId}")
	public ResponseEntity<Dependencia> findByDependenciaId(@PathVariable Integer dependenciaId) {
		return new ResponseEntity<Dependencia>(service.findByDependenciaId(dependenciaId), HttpStatus.OK);
	}

	@GetMapping("/facultad/{facultadId}/{geograficaId}")
	public ResponseEntity<Dependencia> findFirstByFacultadIdAndGeograficaId(@PathVariable Integer facultadId, @PathVariable Integer geograficaId) {
		return new ResponseEntity<Dependencia>(service.findFirstByFacultadIdAndGeograficaId(facultadId, geograficaId), HttpStatus.OK);
	}
}
