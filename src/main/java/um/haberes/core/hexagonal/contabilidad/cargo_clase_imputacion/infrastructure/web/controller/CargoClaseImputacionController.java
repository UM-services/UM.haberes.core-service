package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.web.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.application.exception.CargoClaseImputacionException;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.application.service.CargoClaseImputacionService;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.model.CargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.web.dto.CargoClaseImputacionRequest;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.web.dto.CargoClaseImputacionResponse;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.web.mapper.CargoClaseImputacionDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/cargoclaseimputacion")
@RequiredArgsConstructor
public class CargoClaseImputacionController {

    private final CargoClaseImputacionService cargoClaseImputacionService;
    private final CargoClaseImputacionDtoMapper cargoClaseImputacionDtoMapper;

    @GetMapping("/")
    public ResponseEntity<List<CargoClaseImputacionResponse>> findAll() {
        List<CargoClaseImputacionResponse> responses = cargoClaseImputacionService.getAllCargoClaseImputaciones()
                .stream()
                .map(cargoClaseImputacionDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{cargoClaseImputacionId}")
    public ResponseEntity<CargoClaseImputacionResponse> findByCargoClaseImputacionId(
            @PathVariable Long cargoClaseImputacionId) {
        try {
            CargoClaseImputacion cargoClaseImputacion = cargoClaseImputacionService
                    .getCargoClaseImputacionById(cargoClaseImputacionId);
            return ResponseEntity.ok(cargoClaseImputacionDtoMapper.toResponse(cargoClaseImputacion));
        } catch (CargoClaseImputacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/unique/{dependenciaId}/{facultadId}/{geograficaId}/{cargoClaseId}")
    public ResponseEntity<CargoClaseImputacionResponse> findByUnique(@PathVariable Integer dependenciaId,
            @PathVariable Integer facultadId, @PathVariable Integer geograficaId, @PathVariable Long cargoClaseId) {
        try {
            CargoClaseImputacion cargoClaseImputacion = cargoClaseImputacionService
                    .getCargoClaseImputacionByUnique(dependenciaId, facultadId, geograficaId, cargoClaseId);
            return ResponseEntity.ok(cargoClaseImputacionDtoMapper.toResponse(cargoClaseImputacion));
        } catch (CargoClaseImputacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<CargoClaseImputacionResponse> add(@Valid @RequestBody CargoClaseImputacionRequest request) {
        CargoClaseImputacion domain = cargoClaseImputacionDtoMapper.toDomain(request);
        CargoClaseImputacion created = cargoClaseImputacionService.createCargoClaseImputacion(domain);
        return new ResponseEntity<>(cargoClaseImputacionDtoMapper.toResponse(created), HttpStatus.CREATED);
    }

    @PutMapping("/{cargoClaseImputacionId}")
    public ResponseEntity<CargoClaseImputacionResponse> update(@PathVariable Long cargoClaseImputacionId,
            @Valid @RequestBody CargoClaseImputacionRequest request) {
        CargoClaseImputacion domain = cargoClaseImputacionDtoMapper.toDomain(request);
        try {
            CargoClaseImputacion updated = cargoClaseImputacionService.updateCargoClaseImputacion(cargoClaseImputacionId,
                    domain);
            return ResponseEntity.ok(cargoClaseImputacionDtoMapper.toResponse(updated));
        } catch (CargoClaseImputacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
