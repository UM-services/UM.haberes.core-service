package um.haberes.core.hexagonal.etec.asignacion_cargo.infrastructure.web.controller;

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
import um.haberes.core.hexagonal.etec.asignacion_cargo.application.exception.EtecAsignacionCargoException;
import um.haberes.core.hexagonal.etec.asignacion_cargo.application.service.EtecAsignacionCargoService;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;
import um.haberes.core.hexagonal.etec.asignacion_cargo.infrastructure.web.dto.EtecAsignacionCargoRequest;
import um.haberes.core.hexagonal.etec.asignacion_cargo.infrastructure.web.dto.EtecAsignacionCargoResponse;
import um.haberes.core.hexagonal.etec.asignacion_cargo.infrastructure.web.mapper.EtecAsignacionCargoDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/etec/asignacion-cargo")
@RequiredArgsConstructor
public class EtecAsignacionCargoController {

    private final EtecAsignacionCargoService service;
    private final EtecAsignacionCargoDtoMapper mapper;

    @GetMapping("/legajo/{legajoId}")
    public ResponseEntity<List<EtecAsignacionCargoResponse>> findAllByLegajo(@PathVariable Long legajoId) {
        return ResponseEntity.ok(toResponseList(service.findAllByLegajo(legajoId)));
    }

    @GetMapping("/periodo/{anho}/{mes}")
    public ResponseEntity<List<EtecAsignacionCargoResponse>> findAllVigentesByPeriodo(@PathVariable Integer anho,
            @PathVariable Integer mes) {
        return ResponseEntity.ok(toResponseList(service.findAllVigentesByPeriodo(anho, mes)));
    }

    @GetMapping("/legajo/{legajoId}/periodo/{anho}/{mes}")
    public ResponseEntity<List<EtecAsignacionCargoResponse>> findAllVigentesByLegajoAndPeriodo(
            @PathVariable Long legajoId, @PathVariable Integer anho, @PathVariable Integer mes) {
        return ResponseEntity.ok(toResponseList(service.findAllVigentesByLegajoAndPeriodo(legajoId, anho, mes)));
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Integer>> findAllCategoriasAsignadas() {
        return ResponseEntity.ok(service.findAllCategoriasAsignadas());
    }

    @GetMapping("/categoria/{categoriaId}/exists")
    public ResponseEntity<Boolean> existsByCategoria(@PathVariable Integer categoriaId) {
        return ResponseEntity.ok(service.existsByCategoria(categoriaId));
    }

    @GetMapping("/{asignacionCargoId}")
    public ResponseEntity<EtecAsignacionCargoResponse> findById(@PathVariable Long asignacionCargoId) {
        try {
            return ResponseEntity.ok(mapper.toResponse(service.findById(asignacionCargoId)));
        } catch (EtecAsignacionCargoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<EtecAsignacionCargoResponse> add(
            @Valid @RequestBody EtecAsignacionCargoRequest request) {
        EtecAsignacionCargo created = service.add(mapper.toDomain(request));
        return new ResponseEntity<>(mapper.toResponse(created), HttpStatus.OK);
    }

    @PutMapping("/{asignacionCargoId}")
    public ResponseEntity<EtecAsignacionCargoResponse> update(
            @Valid @RequestBody EtecAsignacionCargoRequest request,
            @PathVariable Long asignacionCargoId) {
        try {
            EtecAsignacionCargo updated = service.update(asignacionCargoId, mapper.toDomain(request));
            return ResponseEntity.ok(mapper.toResponse(updated));
        } catch (EtecAsignacionCargoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{asignacionCargoId}")
    public ResponseEntity<Void> delete(@PathVariable Long asignacionCargoId) {
        service.delete(asignacionCargoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private List<EtecAsignacionCargoResponse> toResponseList(List<EtecAsignacionCargo> asignaciones) {
        return asignaciones.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
