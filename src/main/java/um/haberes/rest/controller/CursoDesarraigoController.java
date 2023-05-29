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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.model.CursoDesarraigo;
import um.haberes.rest.service.CursoDesarraigoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/cursodesarraigo")
public class CursoDesarraigoController {

	@Autowired
	private CursoDesarraigoService service;

	@GetMapping("/")
	public @ResponseBody ResponseEntity<List<CursoDesarraigo>> findAll() {
		return new ResponseEntity<List<CursoDesarraigo>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/legajoId/{legajoId}/{anho}/{mes}")
	public @ResponseBody ResponseEntity<List<CursoDesarraigo>> findAllByLegajoId(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<CursoDesarraigo>>(service.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes),
				HttpStatus.OK);
	}

	@GetMapping("/legajoId/version/{legajoId}/{anho}/{mes}/{version}")
	public @ResponseBody ResponseEntity<List<CursoDesarraigo>> findAllByVersion(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Integer version) {
		return new ResponseEntity<List<CursoDesarraigo>>(service.findAllByVersion(legajoId, anho, mes, version),
				HttpStatus.OK);
	}

	@GetMapping("/{cursodesarraigoId}")
	public @ResponseBody ResponseEntity<CursoDesarraigo> findByCursoDesarraigoId(@PathVariable Long cursoDesarraigoId) {
		return new ResponseEntity<CursoDesarraigo>(service.findByCursoDesarraigoId(cursoDesarraigoId), HttpStatus.OK);
	}

	@GetMapping("/unique/{legajoId}/{anho}/{mes}/{cursoId}")
	public @ResponseBody ResponseEntity<CursoDesarraigo> findByUnique(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Long cursoId) {
		return new ResponseEntity<CursoDesarraigo>(service.findByUnique(legajoId, anho, mes, cursoId), HttpStatus.OK);
	}

	@DeleteMapping("/{cursodesarraigoId}")
	public ResponseEntity<Void> delete(@PathVariable Long cursodesarraigoId) {
		service.delete(cursodesarraigoId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/")
	public ResponseEntity<CursoDesarraigo> add(@RequestBody CursoDesarraigo cursodesarraigo) {
		return new ResponseEntity<CursoDesarraigo>(service.add(cursodesarraigo), HttpStatus.OK);
	}

	@PutMapping("/{cursodesarraigoId}")
	public ResponseEntity<CursoDesarraigo> update(@RequestBody CursoDesarraigo cursodesarraigo,
			@PathVariable Long cursodesarraigoId) {
		return new ResponseEntity<CursoDesarraigo>(service.update(cursodesarraigo, cursodesarraigoId), HttpStatus.OK);
	}

}
