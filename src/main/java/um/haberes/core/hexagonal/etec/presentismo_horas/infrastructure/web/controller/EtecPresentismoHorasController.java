package um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.web.controller;

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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.presentismo_horas.application.exception.EtecPresentismoHorasConflictoException;
import um.haberes.core.hexagonal.etec.presentismo_horas.application.exception.EtecPresentismoHorasException;
import um.haberes.core.hexagonal.etec.presentismo_horas.application.service.EtecPresentismoHorasService;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.model.EtecPresentismoHoras;
import um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.web.dto.EtecPresentismoHorasRequest;
import um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.web.dto.EtecPresentismoHorasResponse;
import um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.web.dto.EtecPresentismoHorasUpdateRequest;
import um.haberes.core.hexagonal.etec.presentismo_horas.infrastructure.web.mapper.EtecPresentismoHorasDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/etec/presentismo-horas")
@RequiredArgsConstructor
public class EtecPresentismoHorasController {

    private final EtecPresentismoHorasService service;

    private final EtecPresentismoHorasDtoMapper mapper;

    /**
     * Estado del legajo/período: siempre responde 200. Si no hay pérdida registrada, el
     * `presentismoHorasId` viene nulo; `tieneCargosHoras` indica si el bloque aplica.
     */
    @GetMapping("/legajo/{legajoId}/periodo/{anho}/{mes}")
    public ResponseEntity<EtecPresentismoHorasResponse> findByLegajoAndPeriodo(
            @PathVariable Long legajoId, @PathVariable Integer anho, @PathVariable Integer mes) {
        EtecPresentismoHorasResponse response = service.findByLegajoAndPeriodo(legajoId, anho, mes)
                .map(mapper::toResponse)
                .orElseGet(() -> EtecPresentismoHorasResponse.builder()
                        .legajoId(legajoId)
                        .anho(anho)
                        .mes(mes)
                        .build());
        response.setTieneCargosHoras(service.tieneCargosHoras(legajoId, anho, mes));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/periodo/{anho}/{mes}")
    public ResponseEntity<List<EtecPresentismoHorasResponse>> findAllByPeriodo(
            @PathVariable Integer anho, @PathVariable Integer mes) {
        return ResponseEntity.ok(service.findAllByPeriodo(anho, mes).stream()
                .map(mapper::toResponse)
                .peek(response -> response.setTieneCargosHoras(true))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{presentismoHorasId}")
    public ResponseEntity<EtecPresentismoHorasResponse> findById(
            @PathVariable Long presentismoHorasId) {
        EtecPresentismoHorasResponse response = mapper.toResponse(service.findById(presentismoHorasId));
        response.setTieneCargosHoras(service.tieneCargosHoras(
                response.getLegajoId(), response.getAnho(), response.getMes()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<EtecPresentismoHorasResponse> add(
            @Valid @RequestBody EtecPresentismoHorasRequest request) {
        EtecPresentismoHoras created = service.add(mapper.toDomain(request));
        EtecPresentismoHorasResponse response = mapper.toResponse(created);
        response.setTieneCargosHoras(true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{presentismoHorasId}")
    public ResponseEntity<EtecPresentismoHorasResponse> update(
            @Valid @RequestBody EtecPresentismoHorasUpdateRequest request,
            @PathVariable Long presentismoHorasId) {
        EtecPresentismoHoras updated = service.update(presentismoHorasId, mapper.toDomain(request));
        EtecPresentismoHorasResponse response = mapper.toResponse(updated);
        response.setTieneCargosHoras(service.tieneCargosHoras(
                response.getLegajoId(), response.getAnho(), response.getMes()));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{presentismoHorasId}")
    public ResponseEntity<Void> delete(@PathVariable Long presentismoHorasId) {
        service.delete(presentismoHorasId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(EtecPresentismoHorasConflictoException.class)
    public ResponseEntity<ProblemDetail> conflicto(EtecPresentismoHorasConflictoException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage()));
    }

    @ExceptionHandler(EtecPresentismoHorasException.class)
    public ResponseEntity<ProblemDetail> error(EtecPresentismoHorasException exception) {
        return ResponseEntity.badRequest()
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }
}
