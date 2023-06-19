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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.kotlin.model.CodigoGrupo;
import um.haberes.rest.service.CodigoGrupoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/codigogrupo")
public class CodigoGrupoController {
	
	@Autowired
	private CodigoGrupoService service;

	@GetMapping("/")
	public ResponseEntity<List<CodigoGrupo>> findAll() {
		return new ResponseEntity<List<CodigoGrupo>>(service.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{codigoId}")
	public ResponseEntity<CodigoGrupo> findByCodigoId(@PathVariable Integer codigoId) {
		return new ResponseEntity<CodigoGrupo>(service.findByCodigoId(codigoId), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<CodigoGrupo> add(@RequestBody CodigoGrupo codigo) {
		return new ResponseEntity<CodigoGrupo>(service.add(codigo), HttpStatus.OK);
	}
	
	@PutMapping("/{codigoId}")
	public ResponseEntity<CodigoGrupo> update(@RequestBody CodigoGrupo codigogrupo, @PathVariable Integer codigoId) {
		return new ResponseEntity<CodigoGrupo>(service.update(codigogrupo, codigoId), HttpStatus.OK);
	}
}
