/**
 * 
 */
package um.haberes.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.model.CursoFusion;
import um.haberes.rest.service.CursoFusionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/cursofusion")
public class CursoFusionController {
	
	@Autowired
	private CursoFusionService service;

	@GetMapping("/legajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<CursoFusion>> findAllByLegajoId(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<CursoFusion>>(service.findAllByLegajoId(legajoId, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/legajofacultad/{legajoId}/{anho}/{mes}/{facultadId}")
	public ResponseEntity<List<CursoFusion>> findAllByLegajoIdAndFacultadId(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Integer facultadId) {
		return new ResponseEntity<List<CursoFusion>>(
				service.findAllByLegajoIdAndFacultadId(legajoId, anho, mes, facultadId), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<CursoFusion> add(@RequestBody CursoFusion cursofusion) {
		return new ResponseEntity<CursoFusion>(service.add(cursofusion), HttpStatus.OK);
	}

	@DeleteMapping("/{cursoFusionId}")
	public ResponseEntity<Void> deleteByCursofusionId(@PathVariable Long cursoFusionId) {
		service.deleteByCursoFusionId(cursoFusionId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/facultad/{legajoId}/{anho}/{mes}/{facultadId}/{geograficaId}")
	public ResponseEntity<Void> deleteByFacultadId(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer facultadId, @PathVariable Integer geograficaId) {
		service.deleteAllByLegajoIdAndAnhoAndMesAndFacultadIdAndGeograficaId(legajoId, anho, mes, facultadId, geograficaId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
