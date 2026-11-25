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
import um.haberes.core.hexagonal.seguridad.application.service.PermisoService;
import um.haberes.core.hexagonal.seguridad.application.service.SeguridadSyncService;
import um.haberes.core.hexagonal.seguridad.domain.model.SeguridadConstantes;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.PermisoRequest;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.PermisoResponse;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.SyncRequest;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.SyncResponse;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.mapper.SeguridadDtoMapper;

import java.util.List;

@RestController
@RequestMapping("/api/haberes/core/permiso")
@RequiredArgsConstructor
public class PermisoController {

    private final PermisoService service;
    private final SeguridadSyncService syncService;
    private final SeguridadDtoMapper mapper;

    @GetMapping({"", "/"})
    public ResponseEntity<List<PermisoResponse>> findAll() {
        return ResponseEntity.ok(service.listar(SeguridadConstantes.APLICACION, false).stream()
                .map(mapper::toResponse).toList());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<PermisoResponse>> findActivos() {
        return ResponseEntity.ok(service.listar(SeguridadConstantes.APLICACION, true).stream()
                .map(mapper::toResponse).toList());
    }

    @GetMapping("/aplicacion/{aplicacion}")
    public ResponseEntity<List<PermisoResponse>> findByAplicacion(@PathVariable String aplicacion) {
        return ResponseEntity.ok(service.listar(aplicacion, false).stream()
                .map(mapper::toResponse).toList());
    }

    @GetMapping("/{permisoId}")
    public ResponseEntity<PermisoResponse> findById(@PathVariable Long permisoId) {
        return ResponseEntity.ok(mapper.toResponse(service.obtener(permisoId)));
    }

    @PostMapping({"", "/"})
    public ResponseEntity<PermisoResponse> create(@RequestBody PermisoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(service.crear(mapper.toDomain(request))));
    }

    @PutMapping("/{permisoId}")
    public ResponseEntity<PermisoResponse> update(@PathVariable Long permisoId,
                                                  @RequestBody PermisoRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.actualizar(permisoId, mapper.toDomain(request))));
    }

    /** Baja lógica: el permiso queda inactivo y conserva sus asignaciones. */
    @DeleteMapping("/{permisoId}")
    public ResponseEntity<Void> delete(@PathVariable Long permisoId) {
        service.retirar(permisoId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/sync")
    public ResponseEntity<SyncResponse> sync(@RequestBody(required = false) SyncRequest request) {
        Boolean dryRun = request == null ? null : request.getDryRun();
        return ResponseEntity.ok(mapper.toResponse(syncService.syncAll(Boolean.TRUE.equals(dryRun))));
    }
}
