package um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.web.controller;

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
import um.haberes.core.hexagonal.liquidaciones.novedad.application.exception.NovedadException;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.service.NovedadService;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.web.dto.NovedadRequest;
import um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.web.dto.NovedadResponse;
import um.haberes.core.hexagonal.liquidaciones.novedad.infrastructure.web.mapper.NovedadDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/novedad")
@RequiredArgsConstructor
public class NovedadController {

	private final NovedadService service;
	private final NovedadDtoMapper novedadDtoMapper;

	@GetMapping("/legajo/{legajoId}/{anho}/{mes}")
	public ResponseEntity<List<NovedadResponse>> findAllByLegajo(@PathVariable Long legajoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return ResponseEntity.ok(toResponseList(service.findAllByLegajo(legajoId, anho, mes)));
	}

	@GetMapping("/codigo/{codigoId}/{anho}/{mes}")
	public ResponseEntity<List<NovedadResponse>> findAllByCodigo(@PathVariable Integer codigoId,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return ResponseEntity.ok(toResponseList(service.findAllByCodigo(codigoId, anho, mes)));
	}

	@GetMapping("/importado/{importado}/{anho}/{mes}")
	public ResponseEntity<List<NovedadResponse>> findAllByImportado(@PathVariable Byte importado,
			@PathVariable Integer anho, @PathVariable Integer mes) {
		return ResponseEntity.ok(toResponseList(service.findAllByImportado(importado, anho, mes)));
	}

	@GetMapping("/{novedadId}")
	public ResponseEntity<NovedadResponse> findByNovedadId(@PathVariable Long novedadId) {
		try {
			return ResponseEntity.ok(novedadDtoMapper.toResponse(service.findByNovedadId(novedadId)));
		} catch (NovedadException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/unique/{legajoId}/{anho}/{mes}/{codigoId}/{dependenciaId}")
	public ResponseEntity<NovedadResponse> findByUnique(@PathVariable Long legajoId, @PathVariable Integer anho,
			@PathVariable Integer mes, @PathVariable Integer codigoId, @PathVariable String dependenciaId) {
		Integer dependenciaIdLocal = null;
		if (!dependenciaId.equals("null"))
			dependenciaIdLocal = Integer.valueOf(dependenciaId);
		try {
			return ResponseEntity.ok(
					novedadDtoMapper.toResponse(service.findByUnique(legajoId, anho, mes, codigoId, dependenciaIdLocal)));
		} catch (NovedadException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<NovedadResponse> add(@Valid @RequestBody NovedadRequest request) {
		Novedad created = service.add(novedadDtoMapper.toDomain(request));
		return new ResponseEntity<>(novedadDtoMapper.toResponse(created), HttpStatus.CREATED);
	}

	@PutMapping("/{novedadId}")
	public ResponseEntity<NovedadResponse> update(@Valid @RequestBody NovedadRequest request,
			@PathVariable Long novedadId) {
		try {
			Novedad updated = service.update(novedadDtoMapper.toDomain(request), novedadId);
			return ResponseEntity.ok(novedadDtoMapper.toResponse(updated));
		} catch (NovedadException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("/{novedadId}")
	public ResponseEntity<Void> deleteByNovedadId(@PathVariable Long novedadId) {
		service.deleteByNovedadId(novedadId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/periodo/{anho}/{mes}")
	public ResponseEntity<Void> deleteAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
		service.deleteAllByPeriodo(anho, mes);
		return ResponseEntity.noContent().build();
	}

	private List<NovedadResponse> toResponseList(List<Novedad> novedades) {
		return novedades.stream()
				.map(novedadDtoMapper::toResponse)
				.collect(Collectors.toList());
	}

}
