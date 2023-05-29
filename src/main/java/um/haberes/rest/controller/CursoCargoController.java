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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import um.haberes.rest.exception.CursoCargoNotFoundException;
import um.haberes.rest.model.CursoCargo;
import um.haberes.rest.service.CursoCargoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/cursoCargo")
public class CursoCargoController {

	@Autowired
	private CursoCargoService service;

	@GetMapping("/legajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<CursoCargo>> findAllByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<CursoCargo>>(service.findAllByLegajo(legajoId, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/legajonivel/{legajoId}/{anho}/{mes}/{nivelId}")
	public ResponseEntity<List<CursoCargo>> findAllByLegajoAndNivel(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Integer nivelId) {
		return new ResponseEntity<List<CursoCargo>>(service.findAllByLegajoAndNivel(legajoId, anho, mes, nivelId),
				HttpStatus.OK);
	}

	@GetMapping("/legajodesarraigo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<CursoCargo>> findAllByLegajoDesarraigo(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<CursoCargo>>(service.findAllByLegajoDesarraigo(legajoId, anho, mes),
				HttpStatus.OK);
	}

	@GetMapping("/curso/{cursoId}/{anho}/{mes}")
	public ResponseEntity<List<CursoCargo>> findAllByCurso(@PathVariable Long cursoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<CursoCargo>>(service.findAllByCurso(cursoId, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/facultad/{legajoId}/{anho}/{mes}/{facultadId}")
	public ResponseEntity<List<CursoCargo>> findAllByFacultad(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer facultadId) {
		return new ResponseEntity<List<CursoCargo>>(service.findAllByFacultad(legajoId, anho, mes, facultadId),
				HttpStatus.OK);
	}

	@GetMapping("/cargoTipo/{legajoId}/{anho}/{mes}/{facultadId}/{geograficaId}/{anual}/{semestre1}/{semestre2}/{cargoTipoId}")
	public ResponseEntity<List<CursoCargo>> findAllByCargoTipo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer facultadId, @PathVariable Integer geograficaId,
			@PathVariable Byte anual, @PathVariable Byte semestre1, @PathVariable Byte semestre2,
			@PathVariable Integer cargoTipoId) {
		return new ResponseEntity<List<CursoCargo>>(service.findAllByCargoTipo(legajoId, anho, mes, facultadId,
				geograficaId, anual, semestre1, semestre2, cargoTipoId), HttpStatus.OK);
	}

	@GetMapping("/cursoany/{cursoId}")
	public ResponseEntity<List<CursoCargo>> findAllByCursoAny(@PathVariable Long cursoId) {
		return new ResponseEntity<List<CursoCargo>>(service.findAnyByCursoId(cursoId), HttpStatus.OK);
	}

	@GetMapping("/periodoany/{anho}/{mes}")
	public ResponseEntity<List<CursoCargo>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<CursoCargo>>(service.findAnyByAnhoAndMes(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/{cursoCargoId}")
	public ResponseEntity<CursoCargo> findByCursoCargoId(@PathVariable Long cursoCargoId) {
		try {
			return new ResponseEntity<CursoCargo>(service.findByCursoCargoId(cursoCargoId), HttpStatus.OK);
		} catch (CursoCargoNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/unique/{cursoId}/{anho}/{mes}/{cargoTipoId}/{legajoId}")
	public ResponseEntity<CursoCargo> findByUnique(@PathVariable Long cursoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer cargoTipoId, @PathVariable Long legajoId) {
		try {
			return new ResponseEntity<CursoCargo>(service.findByUnique(cursoId, anho, mes, cargoTipoId, legajoId),
					HttpStatus.OK);
		} catch (CursoCargoNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/legajo/{cursoId}/{anho}/{mes}/{legajoId}")
	public ResponseEntity<CursoCargo> findByLegajoId(@PathVariable Long cursoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Long legajoId) {
		try {
			return new ResponseEntity<CursoCargo>(service.findByLegajo(cursoId, anho, mes, legajoId), HttpStatus.OK);
		} catch (CursoCargoNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<CursoCargo> add(@RequestBody CursoCargo cursoCargo) {
		return new ResponseEntity<CursoCargo>(service.add(cursoCargo), HttpStatus.OK);
	}

	@PutMapping("/{cursoCargoId}")
	public ResponseEntity<CursoCargo> update(@RequestBody CursoCargo cursoCargo, @PathVariable Long cursoCargoId) {
		return new ResponseEntity<CursoCargo>(service.update(cursoCargo, cursoCargoId), HttpStatus.OK);
	}

	@DeleteMapping("/{cursoCargoId}")
	public ResponseEntity<Void> deleteByCursoCargoId(@PathVariable Long cursoCargoId) {
		service.deleteByCursoCargoId(cursoCargoId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/unique/{cursoId}/{anho}/{mes}/{cargoTipoId}/{legajoId}")
	public ResponseEntity<Void> deleteByUnique(@PathVariable Long cursoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer cargoTipoId, @PathVariable Long legajoId) {
		service.deleteByUnique(cursoId, anho, mes, cargoTipoId, legajoId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
