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
@RequestMapping("/api/haberes/core/codigogrupo")
public class CodigoGrupoController {

	private final CodigoGrupoService service;

	public CodigoGrupoController(CodigoGrupoService service) {
		this.service = service;
	}

	@GetMapping("/")
	public ResponseEntity<List<CodigoGrupo>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{codigoId}")
	public ResponseEntity<CodigoGrupo> findByCodigoId(@PathVariable Integer codigoId) {
		return new ResponseEntity<>(service.findByCodigoId(codigoId), HttpStatus.OK);
	}

	@GetMapping("/noremunerativo/{noRemunerativo}")
	public ResponseEntity<List<CodigoGrupo>> findAllByNoRemunerativo(@PathVariable Byte noRemunerativo) {
		return new ResponseEntity<>(service.findAllByNoRemunerativo(noRemunerativo), HttpStatus.OK);
	}

	@GetMapping("/remunerativo/{remunerativo}")
	public ResponseEntity<List<CodigoGrupo>> findAllByRemunerativo(@PathVariable Byte remunerativo) {
		return new ResponseEntity<>(service.findAllByRemunerativo(remunerativo), HttpStatus.OK);
	}

	@GetMapping("/deduccion/{deduccion}")
	public ResponseEntity<List<CodigoGrupo>> findAllByDeduccion(@PathVariable Byte deduccion) {
		return new ResponseEntity<>(service.findAllByDeduccion(deduccion), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<CodigoGrupo> add(@RequestBody CodigoGrupo codigo) {
		return new ResponseEntity<>(service.add(codigo), HttpStatus.OK);
	}

	@PutMapping("/{codigoId}")
	public ResponseEntity<CodigoGrupo> update(@RequestBody CodigoGrupo codigogrupo, @PathVariable Integer codigoId) {
		return new ResponseEntity<>(service.update(codigogrupo, codigoId), HttpStatus.OK);
	}

}
