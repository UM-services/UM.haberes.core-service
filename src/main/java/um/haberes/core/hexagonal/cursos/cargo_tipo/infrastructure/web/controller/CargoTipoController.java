package um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;
import um.haberes.core.hexagonal.cursos.cargo_tipo.application.exception.CargoTipoException;
import um.haberes.core.hexagonal.cursos.cargo_tipo.application.service.CargoTipoService;
import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.model.CargoTipo;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.web.dto.CargoTipoResponse;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.web.mapper.CargoTipoDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/cargotipo")
@RequiredArgsConstructor
public class CargoTipoController {

	private final CargoTipoService service;
	private final CargoTipoDtoMapper cargoTipoDtoMapper;

	@GetMapping("/")
	public ResponseEntity<List<CargoTipoResponse>> findAll() {
		return ResponseEntity.ok(toResponseList(service.findAll()));
	}

	@GetMapping("/{cargoTipoId}")
	public ResponseEntity<CargoTipoResponse> findByCargoTipoId(@PathVariable Integer cargoTipoId) {
		try {
			return ResponseEntity.ok(cargoTipoDtoMapper.toResponse(service.findByCargoTipoId(cargoTipoId)));
		} catch (CargoTipoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	private List<CargoTipoResponse> toResponseList(List<CargoTipo> cargos) {
		return cargos.stream()
				.map(cargoTipoDtoMapper::toResponse)
				.collect(Collectors.toList());
	}

}
