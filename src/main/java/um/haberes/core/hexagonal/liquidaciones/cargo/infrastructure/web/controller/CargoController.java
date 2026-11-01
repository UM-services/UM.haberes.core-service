package um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.web.controller;

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
import um.haberes.core.hexagonal.liquidaciones.cargo.application.exception.CargoException;
import um.haberes.core.hexagonal.liquidaciones.cargo.application.service.CargoService;
import um.haberes.core.hexagonal.liquidaciones.cargo.domain.model.Cargo;
import um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.web.dto.CargoRequest;
import um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.web.dto.CargoResponse;
import um.haberes.core.hexagonal.liquidaciones.cargo.infrastructure.web.mapper.CargoDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/cargo")
@RequiredArgsConstructor
public class CargoController {

    private final CargoService service;
    private final CargoDtoMapper cargoDtoMapper;

    @GetMapping("/legajo/{legajoId}")
    public ResponseEntity<List<CargoResponse>> findAllByLegajoId(@PathVariable Long legajoId) {
        return ResponseEntity.ok(toResponseList(service.findAllByLegajoId(legajoId)));
    }

    @GetMapping("/{cargoId}")
    public ResponseEntity<CargoResponse> findByCargoId(@PathVariable Long cargoId) {
        try {
            return ResponseEntity.ok(cargoDtoMapper.toResponse(service.findByCargoId(cargoId)));
        } catch (CargoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<CargoResponse> add(@Valid @RequestBody CargoRequest request) {
        Cargo created = service.add(cargoDtoMapper.toDomain(request));
        return new ResponseEntity<>(cargoDtoMapper.toResponse(created), HttpStatus.OK);
    }

    @PutMapping("/{cargoId}")
    public ResponseEntity<CargoResponse> update(@Valid @RequestBody CargoRequest request, @PathVariable Long cargoId) {
        try {
            Cargo updated = service.update(cargoDtoMapper.toDomain(request), cargoId);
            return ResponseEntity.ok(cargoDtoMapper.toResponse(updated));
        } catch (CargoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{cargoId}")
    public ResponseEntity<Void> delete(@PathVariable Long cargoId) {
        service.delete(cargoId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    private List<CargoResponse> toResponseList(List<Cargo> cargos) {
        return cargos.stream()
                .map(cargoDtoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
