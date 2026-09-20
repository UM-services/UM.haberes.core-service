package um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.dependencia.application.exception.DependenciaException;
import um.haberes.core.hexagonal.personas.dependencia.application.service.DependenciaService;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.dto.DependenciaResponse;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.mapper.DependenciaDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/dependencia")
@RequiredArgsConstructor
public class DependenciaController {

    private final DependenciaService service;

    private final DependenciaDtoMapper dtoMapper;

    @GetMapping("/")
    public ResponseEntity<List<DependenciaResponse>> findAll() {
        return ResponseEntity.ok(toResponses(service.findAll()));
    }

    @GetMapping("/context/{facultadId}/{geograficaId}")
    public ResponseEntity<List<DependenciaResponse>> findAllByFacultadIdAndGeograficaId(@PathVariable Integer facultadId,
                                                                                        @PathVariable Integer geograficaId) {
        return ResponseEntity.ok(toResponses(service.findAllByFacultadIdAndGeograficaId(facultadId, geograficaId)));
    }

    @GetMapping("/{dependenciaId}")
    public ResponseEntity<DependenciaResponse> findByDependenciaId(@PathVariable Integer dependenciaId) {
        try {
            return ResponseEntity.ok(dtoMapper.toResponse(service.findByDependenciaId(dependenciaId)));
        } catch (DependenciaException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/facultad/{facultadId}/{geograficaId}")
    public ResponseEntity<DependenciaResponse> findFirstByFacultadIdAndGeograficaId(@PathVariable Integer facultadId,
                                                                                    @PathVariable Integer geograficaId) {
        try {
            return ResponseEntity.ok(dtoMapper.toResponse(service.findFirstByFacultadIdAndGeograficaId(facultadId, geograficaId)));
        } catch (DependenciaException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private List<DependenciaResponse> toResponses(List<Dependencia> dependencias) {
        return dependencias.stream()
                .map(dtoMapper::toResponse)
                .toList();
    }
}
