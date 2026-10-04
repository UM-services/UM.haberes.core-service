package um.haberes.core.hexagonal.cursos.curso_desarraigo.infrastructure.web.controller;

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
import um.haberes.core.hexagonal.cursos.curso_desarraigo.application.exception.CursoDesarraigoException;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.application.service.CursoDesarraigoService;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.domain.model.CursoDesarraigo;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.infrastructure.web.dto.CursoDesarraigoRequest;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.infrastructure.web.dto.CursoDesarraigoResponse;
import um.haberes.core.hexagonal.cursos.curso_desarraigo.infrastructure.web.mapper.CursoDesarraigoDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/cursodesarraigo")
@Slf4j
@RequiredArgsConstructor
public class CursoDesarraigoController {

	private final CursoDesarraigoService service;
	private final CursoDesarraigoDtoMapper cursoDesarraigoDtoMapper;

	@GetMapping("/")
	public ResponseEntity<List<CursoDesarraigoResponse>> findAll() {
		return new ResponseEntity<>(toResponseList(service.findAll()), HttpStatus.OK);
	}

	@GetMapping("/legajoId/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<CursoDesarraigoResponse>> findAllByLegajoId(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return new ResponseEntity<>(toResponseList(service.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes)),
				HttpStatus.OK);
	}

	@GetMapping("/legajoId/version/{legajoId}/{anho}/{mes}/{version}")
	public ResponseEntity<List<CursoDesarraigoResponse>> findAllByVersion(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Integer version) {
		return new ResponseEntity<>(toResponseList(service.findAllByVersion(legajoId, anho, mes, version)),
				HttpStatus.OK);
	}

	@GetMapping("/{cursoDesarraigoId}")
	public ResponseEntity<CursoDesarraigoResponse> findByCursoDesarraigoId(@PathVariable Long cursoDesarraigoId) {
		try {
			return new ResponseEntity<>(
					cursoDesarraigoDtoMapper.toResponse(service.findByCursoDesarraigoId(cursoDesarraigoId)),
					HttpStatus.OK);
		} catch (CursoDesarraigoException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/unique/{legajoId}/{anho}/{mes}/{cursoId}")
	public ResponseEntity<CursoDesarraigoResponse> findByUnique(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Long cursoId) {
		try {
			return new ResponseEntity<>(
					cursoDesarraigoDtoMapper.toResponse(service.findByUnique(legajoId, anho, mes, cursoId)),
					HttpStatus.OK);
		} catch (CursoDesarraigoException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping("/{cursoDesarraigoId}")
	public ResponseEntity<Void> delete(@PathVariable Long cursoDesarraigoId) {
		service.delete(cursoDesarraigoId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/")
	public ResponseEntity<CursoDesarraigoResponse> add(@Valid @RequestBody CursoDesarraigoRequest request) {
		CursoDesarraigo created = service.add(cursoDesarraigoDtoMapper.toDomain(request));
		return new ResponseEntity<>(cursoDesarraigoDtoMapper.toResponse(created), HttpStatus.OK);
	}

	@PutMapping("/{cursoDesarraigoId}")
	public ResponseEntity<CursoDesarraigoResponse> update(@Valid @RequestBody CursoDesarraigoRequest request,
			@PathVariable Long cursoDesarraigoId) {
		try {
			CursoDesarraigo updated = service.update(cursoDesarraigoDtoMapper.toDomain(request), cursoDesarraigoId);
			return new ResponseEntity<>(cursoDesarraigoDtoMapper.toResponse(updated), HttpStatus.OK);
		} catch (CursoDesarraigoException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	private List<CursoDesarraigoResponse> toResponseList(List<CursoDesarraigo> cursoDesarraigos) {
		return cursoDesarraigos.stream()
				.map(cursoDesarraigoDtoMapper::toResponse)
				.collect(Collectors.toList());
	}
}
