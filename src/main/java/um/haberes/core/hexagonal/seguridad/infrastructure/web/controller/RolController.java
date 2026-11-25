package um.haberes.core.hexagonal.seguridad.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
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
import um.haberes.core.hexagonal.seguridad.application.service.RolService;
import um.haberes.core.hexagonal.seguridad.domain.model.SeguridadConstantes;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.RolRequest;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.RolResponse;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.mapper.SeguridadDtoMapper;

import java.util.List;

@RestController
@RequestMapping("/api/haberes/core/rol")
@RequiredArgsConstructor
public class RolController {

    private final RolService service;
    private final SeguridadDtoMapper mapper;

    @GetMapping({"", "/"})
    public ResponseEntity<List<RolResponse>> findAll() {
        return ResponseEntity.ok(service.listar(SeguridadConstantes.APLICACION).stream()
                .map(mapper::toResponse).toList());
    }

    @GetMapping("/{rolId}")
    public ResponseEntity<RolResponse> findById(@PathVariable Long rolId) {
        return ResponseEntity.ok(mapper.toResponse(service.obtener(rolId)));
    }

    @PostMapping({"", "/"})
    public ResponseEntity<RolResponse> create(@RequestBody RolRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(service.crear(mapper.toDomain(request))));
    }

    @PutMapping("/{rolId}")
    public ResponseEntity<RolResponse> update(@PathVariable Long rolId, @RequestBody RolRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.actualizar(rolId, mapper.toDomain(request))));
    }

    @DeleteMapping("/{rolId}")
    public ResponseEntity<Void> delete(@PathVariable Long rolId) {
        service.eliminar(rolId);
        return ResponseEntity.noContent().build();
    }
}
