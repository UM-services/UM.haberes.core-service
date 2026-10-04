package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.web.controller;

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
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.application.exception.LegajoContabilidadException;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.application.service.LegajoContabilidadService;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.model.LegajoContabilidad;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.web.dto.LegajoContabilidadRequest;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.web.dto.LegajoContabilidadResponse;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.infrastructure.web.mapper.LegajoContabilidadDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/legajocontabilidad")
@RequiredArgsConstructor
public class LegajoContabilidadController {

    private final LegajoContabilidadService legajoContabilidadService;
    private final LegajoContabilidadDtoMapper legajoContabilidadDtoMapper;

    @GetMapping("/diferencia/{anho}/{mes}")
    public ResponseEntity<List<LegajoContabilidadResponse>> findAllDiferenciaByPeriodo(@PathVariable Integer anho,
            @PathVariable Integer mes) {
        List<LegajoContabilidadResponse> responses = legajoContabilidadService.findAllDiferenciaByPeriodo(anho, mes)
                .stream()
                .map(legajoContabilidadDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/unique/{legajoId}/{anho}/{mes}")
    public ResponseEntity<LegajoContabilidadResponse> findByUnique(@PathVariable Long legajoId,
            @PathVariable Integer anho, @PathVariable Integer mes) {
        try {
            LegajoContabilidad legajoContabilidad = legajoContabilidadService.findByUnique(legajoId, anho, mes);
            return ResponseEntity.ok(legajoContabilidadDtoMapper.toResponse(legajoContabilidad));
        } catch (LegajoContabilidadException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{legajocontabilidadId}")
    public ResponseEntity<Void> delete(@PathVariable Long legajocontabilidadId) {
        legajoContabilidadService.delete(legajocontabilidadId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/")
    public ResponseEntity<LegajoContabilidadResponse> add(@Valid @RequestBody LegajoContabilidadRequest request) {
        LegajoContabilidad domain = legajoContabilidadDtoMapper.toDomain(request);
        LegajoContabilidad created = legajoContabilidadService.save(domain);
        return new ResponseEntity<>(legajoContabilidadDtoMapper.toResponse(created), HttpStatus.CREATED);
    }

    @PutMapping("/{legajocontabilidadId}")
    public ResponseEntity<LegajoContabilidadResponse> update(@PathVariable Long legajocontabilidadId,
            @Valid @RequestBody LegajoContabilidadRequest request) {
        LegajoContabilidad domain = legajoContabilidadDtoMapper.toDomain(request);
        try {
            LegajoContabilidad updated = legajoContabilidadService.updateLegajoContabilidad(legajocontabilidadId,
                    domain);
            return ResponseEntity.ok(legajoContabilidadDtoMapper.toResponse(updated));
        } catch (LegajoContabilidadException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
