/**
 * 
 */
package um.haberes.core.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import um.haberes.core.exception.AnotadorException;
import um.haberes.core.model.AnotadorEntity;
import um.haberes.core.service.AnotadorService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/anotador")
@RequiredArgsConstructor
public class AnotadorController {

	private final AnotadorService service;

	@GetMapping("/legajo/{legajoId}")
	public ResponseEntity<List<AnotadorEntity>> findAllByLegajo(@PathVariable Long legajoId) {
		return new ResponseEntity<>(service.findAllByLegajo(legajoId), HttpStatus.OK);
	}

	@GetMapping("/pendiente/{anho}/{mes}")
	public ResponseEntity<List<AnotadorEntity>> findPendientes(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findPendientes(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/pendientefiltro/{anho}/{mes}/{filtro}")
	public ResponseEntity<List<AnotadorEntity>> findPendientesByFiltro(@PathVariable Integer anho, @PathVariable Integer mes,
			@PathVariable String filtro) {
		return new ResponseEntity<>(service.findPendientesFiltro(anho, mes, filtro), HttpStatus.OK);
	}

	@GetMapping("/pendientefacultad/{facultadId}/{anho}/{mes}")
	public ResponseEntity<List<AnotadorEntity>> findPendientesByFacultad(@PathVariable Integer facultadId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findPendientesByFacultad(facultadId, anho, mes),
				HttpStatus.OK);
	}

	@GetMapping("/revisado/{anho}/{mes}")
	public ResponseEntity<List<AnotadorEntity>> findRevisados(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findRevisados(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/revisadofiltro/{anho}/{mes}/{filtro}")
	public ResponseEntity<List<AnotadorEntity>> findRevisadosByFiltro(@PathVariable Integer anho, @PathVariable Integer mes,
			@PathVariable String filtro) {
		return new ResponseEntity<>(service.findRevisadosFiltro(anho, mes, filtro), HttpStatus.OK);
	}

	@GetMapping("/revisadofacultad/{facultadId}/{anho}/{mes}")
	public ResponseEntity<List<AnotadorEntity>> findRevisadosByFacultad(@PathVariable Integer facultadId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findRevisadosByFacultad(facultadId, anho, mes),
				HttpStatus.OK);
	}

	@GetMapping("/autorizadofacultad/{facultadId}/{anho}/{mes}")
	public ResponseEntity<List<AnotadorEntity>> findAutorizadosByFacultad(@PathVariable Integer facultadId,
																	@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findAutorizadosByFacultad(facultadId, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/rechazadofacultad/{facultadId}/{anho}/{mes}")
	public ResponseEntity<List<AnotadorEntity>> findRechazadosByFacultad(@PathVariable Integer facultadId,
																   @PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(service.findRechazadosByFacultad(facultadId, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/{anotadorId}")
	public ResponseEntity<AnotadorEntity> findByAnotadorId(@PathVariable Long anotadorId) {
		try {
			return new ResponseEntity<>(service.findByAnotadorId(anotadorId), HttpStatus.OK);
		} catch (AnotadorException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<AnotadorEntity> add(@RequestBody AnotadorEntity anotador) {
		return new ResponseEntity<>(service.add(anotador), HttpStatus.OK);
	}

	@PutMapping("/{anotadorId}")
	public ResponseEntity<AnotadorEntity> update(@RequestBody AnotadorEntity anotador, @PathVariable Long anotadorId) {
		return new ResponseEntity<>(service.update(anotador, anotadorId), HttpStatus.OK);
	}

}
