package um.haberes.core.hexagonal.contabilidad.categoria_imputacion.infrastructure.web.controller;

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
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.application.exception.CategoriaImputacionException;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.application.service.CategoriaImputacionService;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.model.CategoriaImputacion;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.infrastructure.web.dto.CategoriaImputacionRequest;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.infrastructure.web.dto.CategoriaImputacionResponse;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.infrastructure.web.mapper.CategoriaImputacionDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/categoriaimputacion")
@RequiredArgsConstructor
public class CategoriaImputacionController {

    private final CategoriaImputacionService categoriaImputacionService;
    private final CategoriaImputacionDtoMapper categoriaImputacionDtoMapper;

    @GetMapping("/")
    public ResponseEntity<List<CategoriaImputacionResponse>> findAll() {
        List<CategoriaImputacionResponse> responses = categoriaImputacionService.getAllCategoriaImputaciones()
                .stream()
                .map(categoriaImputacionDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{categoriaImputacionId}")
    public ResponseEntity<CategoriaImputacionResponse> findByCategoriaImputacionId(
            @PathVariable Long categoriaImputacionId) {
        try {
            CategoriaImputacion categoriaImputacion = categoriaImputacionService
                    .getCategoriaImputacionById(categoriaImputacionId);
            return ResponseEntity.ok(categoriaImputacionDtoMapper.toResponse(categoriaImputacion));
        } catch (CategoriaImputacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/unique/{dependenciaId}/{facultadId}/{geograficaId}/{categoriaId}")
    public ResponseEntity<CategoriaImputacionResponse> findByUnique(@PathVariable Integer dependenciaId,
            @PathVariable Integer facultadId, @PathVariable Integer geograficaId, @PathVariable Integer categoriaId) {
        try {
            CategoriaImputacion categoriaImputacion = categoriaImputacionService
                    .getCategoriaImputacionByUnique(dependenciaId, facultadId, geograficaId, categoriaId);
            return ResponseEntity.ok(categoriaImputacionDtoMapper.toResponse(categoriaImputacion));
        } catch (CategoriaImputacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<CategoriaImputacionResponse> add(@Valid @RequestBody CategoriaImputacionRequest request) {
        CategoriaImputacion domain = categoriaImputacionDtoMapper.toDomain(request);
        CategoriaImputacion created = categoriaImputacionService.createCategoriaImputacion(domain);
        return new ResponseEntity<>(categoriaImputacionDtoMapper.toResponse(created), HttpStatus.CREATED);
    }

    @PutMapping("/{categoriaImputacionId}")
    public ResponseEntity<CategoriaImputacionResponse> update(@PathVariable Long categoriaImputacionId,
            @Valid @RequestBody CategoriaImputacionRequest request) {
        CategoriaImputacion domain = categoriaImputacionDtoMapper.toDomain(request);
        try {
            CategoriaImputacion updated = categoriaImputacionService.updateCategoriaImputacion(categoriaImputacionId,
                    domain);
            return ResponseEntity.ok(categoriaImputacionDtoMapper.toResponse(updated));
        } catch (CategoriaImputacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
