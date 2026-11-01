package um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.web.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import um.haberes.core.hexagonal.cursos.curso_cargo.application.exception.CursoCargoException;
import um.haberes.core.hexagonal.cursos.curso_cargo.application.service.CursoCargoService;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.web.dto.CursoCargoRequest;
import um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.web.dto.CursoCargoResponse;
import um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.web.mapper.CursoCargoDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/cursoCargo")
@Slf4j
@RequiredArgsConstructor
public class CursoCargoController {

	private final CursoCargoService service;
	private final CursoCargoDtoMapper cursoCargoDtoMapper;

	@GetMapping("/legajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<CursoCargoResponse>> findAllByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
															@PathVariable Integer mes) {
		return new ResponseEntity<>(toResponseList(service.findAllByLegajo(legajoId, anho, mes)), HttpStatus.OK);
	}

	@GetMapping("/legajonivel/{legajoId}/{anho}/{mes}/{nivelId}")
	public ResponseEntity<List<CursoCargoResponse>> findAllByLegajoAndNivel(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Integer nivelId) {
		return new ResponseEntity<>(toResponseList(service.findAllByLegajoAndNivel(legajoId, anho, mes, nivelId)),
				HttpStatus.OK);
	}

	@GetMapping("/legajodesarraigo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<CursoCargoResponse>> findAllByLegajoDesarraigo(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(toResponseList(service.findAllByLegajoDesarraigo(legajoId, anho, mes)),
				HttpStatus.OK);
	}

	@GetMapping("/curso/{cursoId}/{anho}/{mes}")
	public ResponseEntity<List<CursoCargoResponse>> findAllByCurso(@PathVariable Long cursoId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return ResponseEntity.ok(toResponseList(service.findAllByCurso(cursoId, anho, mes)));
	}

	@GetMapping("/facultad/{legajoId}/{anho}/{mes}/{facultadId}")
	public ResponseEntity<List<CursoCargoResponse>> findAllByFacultad(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer facultadId) {
		return new ResponseEntity<>(toResponseList(service.findAllByFacultad(legajoId, anho, mes, facultadId)),
				HttpStatus.OK);
	}

	@GetMapping("/cargoTipo/{legajoId}/{anho}/{mes}/{facultadId}/{geograficaId}/{anual}/{semestre1}/{semestre2}/{cargoTipoId}")
	public ResponseEntity<List<CursoCargoResponse>> findAllByCargoTipo(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer facultadId, @PathVariable Integer geograficaId,
			@PathVariable Byte anual, @PathVariable Byte semestre1, @PathVariable Byte semestre2,
			@PathVariable Integer cargoTipoId) {
		return new ResponseEntity<>(toResponseList(service.findAllByCargoTipo(legajoId, anho, mes, facultadId,
				geograficaId, anual, semestre1, semestre2, cargoTipoId)), HttpStatus.OK);
	}

	@GetMapping("/cursoany/{cursoId}")
	public ResponseEntity<List<CursoCargoResponse>> findAllByCursoAny(@PathVariable Long cursoId) {
		return new ResponseEntity<>(toResponseList(service.findAnyByCursoId(cursoId)), HttpStatus.OK);
	}

	@GetMapping("/periodoany/{anho}/{mes}")
	public ResponseEntity<List<CursoCargoResponse>> findAnyByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(toResponseList(service.findAnyByAnhoAndMes(anho, mes)), HttpStatus.OK);
	}

	@GetMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<List<CursoCargoResponse>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(toResponseList(service.findAllByAnhoAndMes(anho, mes)), HttpStatus.OK);
	}

	@GetMapping("/{cursoCargoId}")
	public ResponseEntity<CursoCargoResponse> findByCursoCargoId(@PathVariable Long cursoCargoId) {
		try {
			return new ResponseEntity<>(cursoCargoDtoMapper.toResponse(service.findByCursoCargoId(cursoCargoId)),
					HttpStatus.OK);
		} catch (CursoCargoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/unique/{cursoId}/{anho}/{mes}/{cargoTipoId}/{legajoId}")
	public ResponseEntity<CursoCargoResponse> findByUnique(@PathVariable Long cursoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer cargoTipoId, @PathVariable Long legajoId) {
		try {
			return new ResponseEntity<>(cursoCargoDtoMapper.toResponse(
					service.findByUnique(cursoId, anho, mes, cargoTipoId, legajoId)), HttpStatus.OK);
		} catch (CursoCargoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/legajo/{cursoId}/{anho}/{mes}/{legajoId}")
	public ResponseEntity<CursoCargoResponse> findByLegajoId(@PathVariable Long cursoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Long legajoId) {
		try {
			return new ResponseEntity<>(
					cursoCargoDtoMapper.toResponse(service.findByLegajo(cursoId, anho, mes, legajoId)), HttpStatus.OK);
		} catch (CursoCargoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<CursoCargoResponse> add(@Valid @RequestBody CursoCargoRequest request) {
		CursoCargo created = service.add(cursoCargoDtoMapper.toDomain(request));
		return new ResponseEntity<>(cursoCargoDtoMapper.toResponse(created), HttpStatus.OK);
	}

	@PutMapping("/{cursoCargoId}")
	public ResponseEntity<CursoCargoResponse> update(@Valid @RequestBody CursoCargoRequest request,
			@PathVariable Long cursoCargoId) {
		try {
			CursoCargo updated = service.update(cursoCargoDtoMapper.toDomain(request), cursoCargoId);
			return new ResponseEntity<>(cursoCargoDtoMapper.toResponse(updated), HttpStatus.OK);
		} catch (CursoCargoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("/{cursoCargoId}")
	public ResponseEntity<Void> deleteByCursoCargoId(@PathVariable Long cursoCargoId) {
		service.deleteByCursoCargoId(cursoCargoId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/unique/{cursoId}/{anho}/{mes}/{cargoTipoId}/{legajoId}")
	public ResponseEntity<Void> deleteByUnique(@PathVariable Long cursoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer cargoTipoId, @PathVariable Long legajoId) {
		service.deleteByUnique(cursoId, anho, mes, cargoTipoId, legajoId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	private List<CursoCargoResponse> toResponseList(List<CursoCargo> cursoCargos) {
		return cursoCargos.stream()
				.map(cursoCargoDtoMapper::toResponse)
				.collect(Collectors.toList());
	}

}
