/**
 * 
 */
package ar.edu.um.haberes.rest.controller;

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

import ar.edu.um.haberes.rest.exception.CursoCargoNovedadNotFoundException;
import ar.edu.um.haberes.rest.model.CursoCargoNovedad;
import ar.edu.um.haberes.rest.service.CursoCargoNovedadService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/cursocargonovedad")
public class CursoCargoNovedadController {

	@Autowired
	private CursoCargoNovedadService service;

	@GetMapping("/pendiente/{anho}/{mes}")
	public ResponseEntity<List<CursoCargoNovedad>> findAllPendientes(@PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<CursoCargoNovedad>>(service.findAllPendientes(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/pendientealta/{anho}/{mes}")
	public ResponseEntity<List<CursoCargoNovedad>> findAllPendientesAlta(@PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<CursoCargoNovedad>>(service.findAllPendientesAlta(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/cursopendientealta/{cursoId}/{anho}/{mes}")
	public ResponseEntity<List<CursoCargoNovedad>> findAllCursoPendientesAlta(@PathVariable Long cursoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<CursoCargoNovedad>>(service.findAllCursoPendientesAlta(cursoId, anho, mes),
				HttpStatus.OK);
	}

	@GetMapping("/autorizadoalta/{anho}/{mes}")
	public ResponseEntity<List<CursoCargoNovedad>> findAllAutorizadosAlta(@PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<CursoCargoNovedad>>(service.findAllAutorizadosAlta(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/rechazadoalta/{anho}/{mes}")
	public ResponseEntity<List<CursoCargoNovedad>> findAllRechazadosAlta(@PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<CursoCargoNovedad>>(service.findAllRechazadosAlta(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/pendientebaja/{anho}/{mes}")
	public ResponseEntity<List<CursoCargoNovedad>> findAllPendientesBaja(@PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<CursoCargoNovedad>>(service.findAllPendientesBaja(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/cursopendientebaja/{cursoId}/{anho}/{mes}")
	public ResponseEntity<List<CursoCargoNovedad>> findAllCursoPendientesBaja(@PathVariable Long cursoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<CursoCargoNovedad>>(service.findAllCursoPendientesBaja(cursoId, anho, mes),
				HttpStatus.OK);
	}

	@GetMapping("/autorizadobaja/{anho}/{mes}")
	public ResponseEntity<List<CursoCargoNovedad>> findAllAutorizadosBaja(@PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<CursoCargoNovedad>>(service.findAllAutorizadosBaja(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/rechazadobaja/{anho}/{mes}")
	public ResponseEntity<List<CursoCargoNovedad>> findAllRechazadosBaja(@PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<List<CursoCargoNovedad>>(service.findAllRechazadosBaja(anho, mes), HttpStatus.OK);
	}

	@GetMapping("/autorizadolegajo/{legajoId}/{cursoId}/{anho}/{mes}")
	public ResponseEntity<List<CursoCargoNovedad>> findAllAutorizadosLegajo(@PathVariable Long legajoId,
			@PathVariable Long cursoId, @PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<CursoCargoNovedad>>(
				service.findAllAutorizadosLegajo(legajoId, cursoId, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/rechazadolegajo/{legajoId}/{cursoId}/{anho}/{mes}")
	public ResponseEntity<List<CursoCargoNovedad>> findAllRechazadosLegajo(@PathVariable Long legajoId,
			@PathVariable Long cursoId, @PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<CursoCargoNovedad>>(
				service.findAllRechazadosLegajo(legajoId, cursoId, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/pendientelegajo/{legajoId}/{cursoId}/{anho}/{mes}")
	public ResponseEntity<List<CursoCargoNovedad>> findAllPendientesLegajo(@PathVariable Long legajoId,
			@PathVariable Long cursoId, @PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<List<CursoCargoNovedad>>(
				service.findAllPendientesLegajo(legajoId, cursoId, anho, mes), HttpStatus.OK);
	}

	@GetMapping("/{cursoCargoNovedadId}")
	public ResponseEntity<CursoCargoNovedad> findByCursocargonovedadId(@PathVariable Long cursoCargoNovedadId) {
		try {
			return new ResponseEntity<CursoCargoNovedad>(service.findByCursoCargoNovedadId(cursoCargoNovedadId),
					HttpStatus.OK);
		} catch (CursoCargoNovedadNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/legajo/{legajoId}/{cursoId}/{anho}/{mes}")
	public ResponseEntity<CursoCargoNovedad> findByLegajo(@PathVariable Long legajoId, @PathVariable Long cursoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		try {
			return new ResponseEntity<CursoCargoNovedad>(service.findByLegajo(legajoId, cursoId, anho, mes),
					HttpStatus.OK);
		} catch (CursoCargoNovedadNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/unique/{cursoId}/{anho}/{mes}/{cargoTipoId}/{legajoId}")
	public ResponseEntity<CursoCargoNovedad> findByUnique(@PathVariable Long cursoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer cargoTipoId, @PathVariable Long legajoId) {
		try {
			return new ResponseEntity<CursoCargoNovedad>(
					service.findByUnique(cursoId, anho, mes, cargoTipoId, legajoId), HttpStatus.OK);
		} catch (CursoCargoNovedadNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<CursoCargoNovedad> add(@RequestBody CursoCargoNovedad cursoCargoNovedad) {
		return new ResponseEntity<CursoCargoNovedad>(service.add(cursoCargoNovedad), HttpStatus.OK);
	}

	@PutMapping("/{cursoCargoNovedadId}")
	public ResponseEntity<CursoCargoNovedad> update(@RequestBody CursoCargoNovedad cursoCargoNovedad,
			@PathVariable Long cursoCargoNovedadId) {
		return new ResponseEntity<CursoCargoNovedad>(service.update(cursoCargoNovedad, cursoCargoNovedadId),
				HttpStatus.OK);
	}

	@DeleteMapping("/legajoPendiente/{legajoId}/{cursoId}/{anho}/{mes}")
	public ResponseEntity<Void> deleteAllByLegajoPendiente(@PathVariable Long legajoId, @PathVariable Long cursoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		service.deleteAllByLegajoPendiente(legajoId, cursoId, anho, mes);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
