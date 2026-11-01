package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.infrastructure.web.controller;

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
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.application.exception.CodigoImputacionException;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.application.service.CodigoImputacionService;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.model.CodigoImputacion;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.infrastructure.web.dto.CodigoImputacionRequest;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.infrastructure.web.dto.CodigoImputacionResponse;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.infrastructure.web.mapper.CodigoImputacionDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/codigoimputacion")
@RequiredArgsConstructor
public class CodigoImputacionController {

    private final CodigoImputacionService codigoImputacionService;
    private final CodigoImputacionDtoMapper codigoImputacionDtoMapper;

    @GetMapping("/")
    public ResponseEntity<List<CodigoImputacionResponse>> findAll() {
        List<CodigoImputacionResponse> responses = codigoImputacionService.getAllCodigoImputaciones()
                .stream()
                .map(codigoImputacionDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{codigoimputacionId}")
    public ResponseEntity<CodigoImputacionResponse> findByCodigoImputacionId(
            @PathVariable Long codigoimputacionId) {
        try {
            CodigoImputacion codigoImputacion = codigoImputacionService.getCodigoImputacionById(codigoimputacionId);
            return ResponseEntity.ok(codigoImputacionDtoMapper.toResponse(codigoImputacion));
        } catch (CodigoImputacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/unique/{dependenciaId}/{facultadId}/{geograficaId}/{codigoId}")
    public ResponseEntity<CodigoImputacionResponse> findByUnique(@PathVariable Integer dependenciaId,
            @PathVariable Integer facultadId, @PathVariable Integer geograficaId, @PathVariable Integer codigoId) {
        try {
            CodigoImputacion codigoImputacion = codigoImputacionService.getCodigoImputacionByUnique(dependenciaId,
                    facultadId, geograficaId, codigoId);
            return ResponseEntity.ok(codigoImputacionDtoMapper.toResponse(codigoImputacion));
        } catch (CodigoImputacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<CodigoImputacionResponse> add(@Valid @RequestBody CodigoImputacionRequest request) {
        CodigoImputacion domain = codigoImputacionDtoMapper.toDomain(request);
        CodigoImputacion created = codigoImputacionService.createCodigoImputacion(domain);
        return new ResponseEntity<>(codigoImputacionDtoMapper.toResponse(created), HttpStatus.CREATED);
    }

    @PutMapping("/{codigoimputacionId}")
    public ResponseEntity<CodigoImputacionResponse> update(@PathVariable Long codigoimputacionId,
            @Valid @RequestBody CodigoImputacionRequest request) {
        CodigoImputacion domain = codigoImputacionDtoMapper.toDomain(request);
        try {
            CodigoImputacion updated = codigoImputacionService.updateCodigoImputacion(codigoimputacionId, domain);
            return ResponseEntity.ok(codigoImputacionDtoMapper.toResponse(updated));
        } catch (CodigoImputacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
