package um.haberes.core.hexagonal.etec.control_etec.infrastructure.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.control_etec.application.exception.ControlEtecException;
import um.haberes.core.hexagonal.etec.control_etec.application.exception.ControlEtecNoEncontradoException;
import um.haberes.core.hexagonal.etec.control_etec.application.exception.ControlEtecOrigenException;
import um.haberes.core.hexagonal.etec.control_etec.application.exception.ControlEtecYaExisteException;
import um.haberes.core.hexagonal.etec.control_etec.application.service.ControlEtecService;
import um.haberes.core.hexagonal.etec.control_etec.infrastructure.web.dto.ControlEtecRequest;
import um.haberes.core.hexagonal.etec.control_etec.infrastructure.web.dto.ControlEtecResponse;
import um.haberes.core.hexagonal.etec.control_etec.infrastructure.web.mapper.ControlEtecDtoMapper;

/**
 * Control ETEC por período. El alta y la edición usan el mismo {@code POST} (upsert por período)
 * para que el frontend no tenga que distinguir si el período ya existe.
 */
@RestController
@RequestMapping("/api/haberes/core/etec/control")
@RequiredArgsConstructor
public class ControlEtecController {

    private final ControlEtecService service;

    private final ControlEtecDtoMapper mapper;

    @GetMapping("/{anho}/{mes}")
    public ResponseEntity<ControlEtecResponse> findByPeriodo(@PathVariable Integer anho,
                                                             @PathVariable Integer mes) {
        return service.findByPeriodo(anho, mes)
                .map(control -> ResponseEntity.ok(mapper.toResponse(control)))
                .orElseThrow(() -> new ControlEtecNoEncontradoException(anho, mes));
    }

    /** Igual que el anterior pero devuelve 200 con cuerpo vacío si el período no tiene datos. */
    @GetMapping("/{anho}/{mes}/opcional")
    public ResponseEntity<ControlEtecResponse> findByPeriodoOpcional(@PathVariable Integer anho,
                                                                     @PathVariable Integer mes) {
        return ResponseEntity.ok(service.findByPeriodo(anho, mes)
                .map(mapper::toResponse)
                .orElse(null));
    }

    @PostMapping("/{anho}/{mes}")
    public ResponseEntity<ControlEtecResponse> save(@PathVariable Integer anho, @PathVariable Integer mes,
                                                    @RequestBody ControlEtecRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.save(anho, mes, mapper.toDomain(request))));
    }

    @PostMapping("/copiar/{anho}/{mes}")
    public ResponseEntity<ControlEtecResponse> copiar(@PathVariable Integer anho, @PathVariable Integer mes) {
        return ResponseEntity.ok(mapper.toResponse(service.copiar(anho, mes)));
    }

    @ExceptionHandler(ControlEtecYaExisteException.class)
    public ResponseEntity<ProblemDetail> handleYaExiste(ControlEtecYaExisteException e) {
        return new ResponseEntity<>(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ControlEtecNoEncontradoException.class)
    public ResponseEntity<ProblemDetail> handleNoEncontrado(ControlEtecNoEncontradoException e) {
        return new ResponseEntity<>(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ControlEtecOrigenException.class)
    public ResponseEntity<ProblemDetail> handleOrigen(ControlEtecOrigenException e) {
        return new ResponseEntity<>(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ControlEtecException.class)
    public ResponseEntity<ProblemDetail> handleValidacion(ControlEtecException e) {
        return new ResponseEntity<>(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
