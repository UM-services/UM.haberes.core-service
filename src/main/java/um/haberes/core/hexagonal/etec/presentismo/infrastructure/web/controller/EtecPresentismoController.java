package um.haberes.core.hexagonal.etec.presentismo.infrastructure.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import um.haberes.core.hexagonal.etec.presentismo.application.exception.EtecPresentismoConflictoException;
import um.haberes.core.hexagonal.etec.presentismo.application.exception.EtecPresentismoException;
import um.haberes.core.hexagonal.etec.presentismo.application.service.EtecPresentismoService;
import um.haberes.core.hexagonal.etec.presentismo.domain.model.EtecPresentismo;
import um.haberes.core.hexagonal.etec.presentismo.infrastructure.web.dto.EtecPresentismoRequest;
import um.haberes.core.hexagonal.etec.presentismo.infrastructure.web.dto.EtecPresentismoResponse;
import um.haberes.core.hexagonal.etec.presentismo.infrastructure.web.dto.EtecPresentismoUpdateRequest;
import um.haberes.core.hexagonal.etec.presentismo.infrastructure.web.mapper.EtecPresentismoDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/etec/presentismo")
@RequiredArgsConstructor
public class EtecPresentismoController {

    private final EtecPresentismoService service;

    private final EtecPresentismoDtoMapper mapper;

    @GetMapping("/legajo/{legajoId}/periodo/{anho}/{mes}")
    public ResponseEntity<List<EtecPresentismoResponse>> findAllByLegajoAndPeriodo(
            @PathVariable Long legajoId, @PathVariable Integer anho, @PathVariable Integer mes) {
        return ResponseEntity.ok(toResponseList(service.findAllByLegajoAndPeriodo(legajoId, anho, mes)));
    }

    @GetMapping("/periodo/{anho}/{mes}")
    public ResponseEntity<List<EtecPresentismoResponse>> findAllByPeriodo(
            @PathVariable Integer anho, @PathVariable Integer mes) {
        return ResponseEntity.ok(toResponseList(service.findAllByPeriodo(anho, mes)));
    }

    @GetMapping("/asignacion/{asignacionCargoId}/periodo/{anho}/{mes}")
    public ResponseEntity<EtecPresentismoResponse> findByAsignacionAndPeriodo(
            @PathVariable Long asignacionCargoId, @PathVariable Integer anho, @PathVariable Integer mes) {
        return service.findByAsignacionAndPeriodo(asignacionCargoId, anho, mes)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{presentismoId}")
    public ResponseEntity<EtecPresentismoResponse> findById(@PathVariable Long presentismoId) {
        try {
            return ResponseEntity.ok(mapper.toResponse(service.findById(presentismoId)));
        } catch (EtecPresentismoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<EtecPresentismoResponse> add(@Valid @RequestBody EtecPresentismoRequest request) {
        EtecPresentismo created = service.add(mapper.toDomain(request));
        return new ResponseEntity<>(mapper.toResponse(created), HttpStatus.CREATED);
    }

    @PutMapping("/{presentismoId}")
    public ResponseEntity<EtecPresentismoResponse> update(
            @Valid @RequestBody EtecPresentismoUpdateRequest request,
            @PathVariable Long presentismoId) {
        EtecPresentismo updated = service.update(presentismoId, mapper.toDomain(request));
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{presentismoId}")
    public ResponseEntity<Void> delete(@PathVariable Long presentismoId) {
        service.delete(presentismoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(EtecPresentismoConflictoException.class)
    public ResponseEntity<ProblemDetail> conflicto(EtecPresentismoConflictoException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage()));
    }

    @ExceptionHandler(EtecPresentismoException.class)
    public ResponseEntity<ProblemDetail> error(EtecPresentismoException exception) {
        return ResponseEntity.badRequest()
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    private List<EtecPresentismoResponse> toResponseList(List<EtecPresentismo> presentismos) {
        return presentismos.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
