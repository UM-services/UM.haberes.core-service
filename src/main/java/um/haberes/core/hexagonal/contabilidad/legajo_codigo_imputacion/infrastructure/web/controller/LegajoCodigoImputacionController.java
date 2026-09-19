package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.infrastructure.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.application.service.LegajoCodigoImputacionService;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.infrastructure.web.dto.LegajoCodigoImputacionResponse;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.infrastructure.web.mapper.LegajoCodigoImputacionDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/legajocodigoimputacion")
@RequiredArgsConstructor
public class LegajoCodigoImputacionController {

    private final LegajoCodigoImputacionService legajoCodigoImputacionService;
    private final LegajoCodigoImputacionDtoMapper legajoCodigoImputacionDtoMapper;

    @GetMapping("/legajo/{legajoId}/{anho}/{mes}")
    public ResponseEntity<List<LegajoCodigoImputacionResponse>> findAllByLegajo(@PathVariable Long legajoId,
            @PathVariable Integer anho, @PathVariable Integer mes) {
        List<LegajoCodigoImputacionResponse> responses = legajoCodigoImputacionService
                .findAllByLegajo(legajoId, anho, mes)
                .stream()
                .map(legajoCodigoImputacionDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
