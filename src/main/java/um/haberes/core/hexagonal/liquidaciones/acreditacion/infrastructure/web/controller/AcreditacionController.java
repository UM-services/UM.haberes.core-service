package um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.exception.AcreditacionException;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.service.AcreditacionService;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.web.dto.AcreditacionRequest;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.web.dto.AcreditacionResponse;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.web.mapper.AcreditacionDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/acreditacion")
@RequiredArgsConstructor
public class AcreditacionController {

    private final AcreditacionService acreditacionService;
    private final AcreditacionDtoMapper acreditacionDtoMapper;

    @GetMapping("/")
    public ResponseEntity<List<AcreditacionResponse>> findAll() {
        List<AcreditacionResponse> responses = acreditacionService.findAll().stream()
                .map(acreditacionDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{acreditacionId}")
    public ResponseEntity<AcreditacionResponse> findByAcreditacionId(@PathVariable Long acreditacionId) {
        try {
            Acreditacion acreditacion = acreditacionService.findByAcreditacionId(acreditacionId);
            return ResponseEntity.ok(acreditacionDtoMapper.toResponse(acreditacion));
        } catch (AcreditacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/periodo/{anho}/{mes}")
    public ResponseEntity<AcreditacionResponse> findByPeriodo(@PathVariable Integer anho,
            @PathVariable Integer mes) {
        try {
            Acreditacion acreditacion = acreditacionService.findByPeriodo(anho, mes);
            return ResponseEntity.ok(acreditacionDtoMapper.toResponse(acreditacion));
        } catch (AcreditacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{acreditacionId}")
    public ResponseEntity<Void> delete(@PathVariable Long acreditacionId) {
        acreditacionService.delete(acreditacionId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/")
    public ResponseEntity<AcreditacionResponse> add(@Valid @RequestBody AcreditacionRequest request) {
        Acreditacion created = acreditacionService.add(acreditacionDtoMapper.toDomain(request));
        return new ResponseEntity<>(acreditacionDtoMapper.toResponse(created), HttpStatus.CREATED);
    }

    @PutMapping("/{acreditacionId}")
    public ResponseEntity<AcreditacionResponse> update(@Valid @RequestBody AcreditacionRequest request,
            @PathVariable Long acreditacionId) {
        try {
            Acreditacion updated = acreditacionService.update(acreditacionDtoMapper.toDomain(request),
                    acreditacionId);
            return ResponseEntity.ok(acreditacionDtoMapper.toResponse(updated));
        } catch (AcreditacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
