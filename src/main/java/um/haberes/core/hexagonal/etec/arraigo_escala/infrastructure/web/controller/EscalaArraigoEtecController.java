package um.haberes.core.hexagonal.etec.arraigo_escala.infrastructure.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.arraigo_escala.application.exception.EscalaArraigoEtecException;
import um.haberes.core.hexagonal.etec.arraigo_escala.application.service.EscalaArraigoEtecService;
import um.haberes.core.hexagonal.etec.arraigo_escala.infrastructure.web.dto.EscalaArraigoEtecRequest;
import um.haberes.core.hexagonal.etec.arraigo_escala.infrastructure.web.dto.EscalaArraigoEtecResponse;
import um.haberes.core.hexagonal.etec.arraigo_escala.infrastructure.web.mapper.EscalaArraigoEtecDtoMapper;

/**
 * Escala global de arraigo ETEC por antigüedad docente. Se lee completa y se reemplaza completa:
 * la pantalla de Control del período envía los tramos editados en un solo {@code PUT}.
 */
@RestController
@RequestMapping("/api/haberes/core/etec/arraigo/escala")
@RequiredArgsConstructor
public class EscalaArraigoEtecController {

    private final EscalaArraigoEtecService service;

    private final EscalaArraigoEtecDtoMapper mapper;

    @GetMapping
    public ResponseEntity<List<EscalaArraigoEtecResponse>> findAll() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toResponse).toList());
    }

    @PutMapping
    public ResponseEntity<List<EscalaArraigoEtecResponse>> reemplazar(
            @RequestBody List<EscalaArraigoEtecRequest> tramos) {
        return ResponseEntity.ok(service.reemplazar(tramos.stream().map(mapper::toDomain).toList())
                .stream().map(mapper::toResponse).toList());
    }

    @ExceptionHandler(EscalaArraigoEtecException.class)
    public ResponseEntity<ProblemDetail> handleValidacion(EscalaArraigoEtecException e) {
        return new ResponseEntity<>(
                ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
