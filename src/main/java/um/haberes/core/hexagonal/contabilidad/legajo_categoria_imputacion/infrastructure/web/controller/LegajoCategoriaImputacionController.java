package um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.infrastructure.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.application.service.LegajoCategoriaImputacionService;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.infrastructure.web.dto.LegajoCategoriaImputacionResponse;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.infrastructure.web.mapper.LegajoCategoriaImputacionDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/legajocategoriaimputacion")
@RequiredArgsConstructor
public class LegajoCategoriaImputacionController {

    private final LegajoCategoriaImputacionService legajoCategoriaImputacionService;
    private final LegajoCategoriaImputacionDtoMapper legajoCategoriaImputacionDtoMapper;

    @GetMapping("/legajo/{legajoId}/{anho}/{mes}")
    public ResponseEntity<List<LegajoCategoriaImputacionResponse>> findAllByLegajo(@PathVariable Long legajoId,
            @PathVariable Integer anho, @PathVariable Integer mes) {
        List<LegajoCategoriaImputacionResponse> responses = legajoCategoriaImputacionService
                .findAllByLegajo(legajoId, anho, mes)
                .stream()
                .map(legajoCategoriaImputacionDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
