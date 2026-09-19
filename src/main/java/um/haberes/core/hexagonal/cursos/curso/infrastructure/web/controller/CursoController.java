package um.haberes.core.hexagonal.cursos.curso.infrastructure.web.controller;

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
import um.haberes.core.hexagonal.cursos.curso.application.exception.CursoException;
import um.haberes.core.hexagonal.cursos.curso.application.service.CursoService;
import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso.infrastructure.web.dto.CursoRequest;
import um.haberes.core.hexagonal.cursos.curso.infrastructure.web.dto.CursoResponse;
import um.haberes.core.hexagonal.cursos.curso.infrastructure.web.mapper.CursoDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/curso")
@Slf4j
@RequiredArgsConstructor
public class CursoController {

	private final CursoService service;
	private final CursoDtoMapper cursoDtoMapper;

	@GetMapping("/")
	public ResponseEntity<List<CursoResponse>> findAll() {
		return new ResponseEntity<>(toResponseList(service.findAll()), HttpStatus.OK);
	}

	@PostMapping("/geografica/{facultadId}/{geograficaId}")
	public ResponseEntity<List<CursoResponse>> findAllByGeograficaAndConditions(@RequestBody List<String> conditions,
			@PathVariable Integer facultadId, @PathVariable Integer geograficaId) {
		return new ResponseEntity<>(
				toResponseList(service.findAllByGeograficaAndConditions(facultadId, geograficaId, conditions)),
				HttpStatus.OK);
	}

	@GetMapping("/geograficasinfiltro/{facultadId}/{geograficaId}")
	public ResponseEntity<List<CursoResponse>> findAllByFacultadIdAndGeograficaId(@PathVariable Integer facultadId,
			@PathVariable Integer geograficaId) {
		return new ResponseEntity<>(
				toResponseList(service.findAllByFacultadIdAndGeograficaId(facultadId, geograficaId)), HttpStatus.OK);
	}

	@GetMapping("/facultad/{facultadId}/geografica/{geograficaId}/periodo/{anho}/{mes}")
	public ResponseEntity<List<CursoResponse>> findAllByFacultadIdAndGeograficaIdAndAnhoAndMes(
			@PathVariable Integer facultadId, @PathVariable Integer geograficaId, @PathVariable Integer anho,
			@PathVariable Integer mes) {
		return new ResponseEntity<>(
				toResponseList(service.findAllByFacultadIdAndGeograficaIdAndAnhoAndMes(facultadId, geograficaId, anho,
						mes)),
				HttpStatus.OK);
	}

	@GetMapping("/{cursoId}")
	public ResponseEntity<CursoResponse> findByCursoId(@PathVariable Long cursoId) {
		try {
			return new ResponseEntity<>(cursoDtoMapper.toResponse(service.findByCursoId(cursoId)), HttpStatus.OK);
		} catch (CursoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<CursoResponse> add(@Valid @RequestBody CursoRequest request) {
		Curso created = service.add(cursoDtoMapper.toDomain(request));
		return new ResponseEntity<>(cursoDtoMapper.toResponse(created), HttpStatus.OK);
	}

	@PutMapping("/{cursoId}")
	public ResponseEntity<CursoResponse> update(@Valid @RequestBody CursoRequest request,
			@PathVariable Long cursoId) {
		try {
			Curso updated = service.update(cursoDtoMapper.toDomain(request), cursoId);
			return new ResponseEntity<>(cursoDtoMapper.toResponse(updated), HttpStatus.OK);
		} catch (CursoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("/{cursoId}")
	public ResponseEntity<Void> deleteByCursoId(@PathVariable Long cursoId) {
		service.deleteByCursoId(cursoId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	private List<CursoResponse> toResponseList(List<Curso> cursos) {
		return cursos.stream()
				.map(cursoDtoMapper::toResponse)
				.collect(Collectors.toList());
	}

}
