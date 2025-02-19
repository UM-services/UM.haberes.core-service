/**
 * 
 */
package um.haberes.core.controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.kotlin.model.Curso;
import um.haberes.core.service.CursoCargoService;
import um.haberes.core.service.CursoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/curso")
@Slf4j
public class CursoController {

	private final CursoService service;
	private final CursoCargoService cursoCargoService;

	@Autowired
	public CursoController(CursoService service, CursoCargoService cursoCargoService) {
		this.service = service;
		this.cursoCargoService = cursoCargoService;
	}

	@GetMapping("/")
	public ResponseEntity<List<Curso>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@PostMapping("/geografica/{facultadId}/{geograficaId}")
	public ResponseEntity<List<Curso>> findAllByGeograficaAndConditions(@RequestBody List<String> conditions,
			@PathVariable Integer facultadId, @PathVariable Integer geograficaId) {
		return new ResponseEntity<>(
				service.findAllByGeograficaAndConditions(facultadId, geograficaId, conditions), HttpStatus.OK);
	}

	@GetMapping("/geograficasinfiltro/{facultadId}/{geograficaId}")
	public ResponseEntity<List<Curso>> findAllByFacultadIdAndGeograficaId(@PathVariable Integer facultadId,
			@PathVariable Integer geograficaId) {
		return new ResponseEntity<>(service.findAllByFacultadIdAndGeograficaId(facultadId, geograficaId),
				HttpStatus.OK);
	}

	@GetMapping("/facultad/{facultadId}/geografica/{geograficaId}/periodo/{anho}/{mes}")
	public ResponseEntity<List<Curso>> findAllByFacultadIdAndGeograficaIdAndAnhoAndMes(@PathVariable Integer facultadId, @PathVariable Integer geograficaId, @PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findAllByFacultadIdAndGeograficaIdAndAnhoAndMes(facultadId, geograficaId, anho, mes, cursoCargoService), HttpStatus.OK);
	}


		@GetMapping("/{cursoId}")
	public ResponseEntity<Curso> findByCursoId(@PathVariable Long cursoId) {
		Curso curso = service.findByCursoId(cursoId);
        try {
            log.debug("Curso -> {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(curso));
        } catch (JsonProcessingException e) {
            log.debug("Curso -> null");
        }
        return new ResponseEntity<>(curso, HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<Curso> add(@RequestBody Curso curso) {
		return new ResponseEntity<>(service.add(curso), HttpStatus.OK);
	}

	@PutMapping("/{cursoId}")
	public ResponseEntity<Curso> update(@RequestBody Curso curso, @PathVariable Long cursoId) {
		return new ResponseEntity<>(service.update(curso, cursoId), HttpStatus.OK);
	}

	@DeleteMapping("/{cursoId}")
	public ResponseEntity<Void> deleteByCursoId(@PathVariable Long cursoId) {
		service.deleteByCursoId(cursoId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
