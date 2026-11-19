package um.haberes.core.hexagonal.etec.hora_escala.infrastructure.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.hora_escala.application.exception.EscalaHoraEtecException;
import um.haberes.core.hexagonal.etec.hora_escala.application.exception.EscalaHoraEtecPeriodoException;
import um.haberes.core.hexagonal.etec.hora_escala.application.service.EscalaHoraEtecService;
import um.haberes.core.hexagonal.etec.hora_escala.infrastructure.web.dto.EscalaHoraEtecRequest;
import um.haberes.core.hexagonal.etec.hora_escala.infrastructure.web.dto.EscalaHoraEtecResponse;
import um.haberes.core.hexagonal.etec.hora_escala.infrastructure.web.mapper.EscalaHoraEtecDtoMapper;

/**
 * Escala de horas de clase ETEC del período. Se lee completa y se reemplaza completa desde la
 * pantalla de Control del período, igual que la escala de arraigo.
 */
@RestController
@RequestMapping("/api/haberes/core/etec/hora/escala")
@RequiredArgsConstructor
public class EscalaHoraEtecController {

    private final EscalaHoraEtecService service;

    private final EscalaHoraEtecDtoMapper mapper;

    @GetMapping("/{anho}/{mes}")
    public ResponseEntity<List<EscalaHoraEtecResponse>> findByPeriodo(@PathVariable Integer anho,
                                                                      @PathVariable Integer mes) {
        return ResponseEntity.ok(service.findByPeriodo(anho, mes).stream()
                .map(mapper::toResponse)
                .toList());
    }

    @PutMapping("/{anho}/{mes}")
    public ResponseEntity<List<EscalaHoraEtecResponse>> reemplazar(@PathVariable Integer anho,
                                                                   @PathVariable Integer mes,
                                                                   @RequestBody List<EscalaHoraEtecRequest> tramos) {
        return ResponseEntity.ok(service.reemplazar(anho, mes,
                        tramos.stream().map(mapper::toDomain).toList())
                .stream().map(mapper::toResponse).toList());
    }

    @ExceptionHandler(EscalaHoraEtecException.class)
    public ResponseEntity<ProblemDetail> handleValidacion(EscalaHoraEtecException e) {
        return new ResponseEntity<>(
                ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EscalaHoraEtecPeriodoException.class)
    public ResponseEntity<ProblemDetail> handlePeriodo(EscalaHoraEtecPeriodoException e) {
        return new ResponseEntity<>(
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}
