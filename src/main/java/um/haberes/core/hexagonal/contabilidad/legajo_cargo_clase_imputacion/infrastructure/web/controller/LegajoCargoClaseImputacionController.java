package um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.infrastructure.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.application.service.LegajoCargoClaseImputacionService;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.infrastructure.web.dto.LegajoCargoClaseImputacionResponse;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.infrastructure.web.mapper.LegajoCargoClaseImputacionDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/legajocargoclaseimputacion")
@RequiredArgsConstructor
public class LegajoCargoClaseImputacionController {

    private final LegajoCargoClaseImputacionService legajoCargoClaseImputacionService;
    private final LegajoCargoClaseImputacionDtoMapper legajoCargoClaseImputacionDtoMapper;

    @GetMapping("/legajo/{legajoId}/{anho}/{mes}")
    public ResponseEntity<List<LegajoCargoClaseImputacionResponse>> findAllByLegajo(@PathVariable Long legajoId,
            @PathVariable Integer anho, @PathVariable Integer mes) {
        List<LegajoCargoClaseImputacionResponse> responses = legajoCargoClaseImputacionService
                .findAllByLegajo(legajoId, anho, mes)
                .stream()
                .map(legajoCargoClaseImputacionDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
