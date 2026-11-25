package um.haberes.core.hexagonal.seguridad.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.haberes.core.hexagonal.seguridad.application.service.RolPermisoService;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.RolPermisoRequest;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.RolPermisoResponse;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.mapper.SeguridadDtoMapper;

import java.util.List;

@RestController
@RequestMapping("/api/haberes/core/rolPermiso")
@RequiredArgsConstructor
public class RolPermisoController {

    private final RolPermisoService service;
    private final SeguridadDtoMapper mapper;

    @GetMapping("/rol/{rolId}")
    public ResponseEntity<List<RolPermisoResponse>> findByRol(@PathVariable Long rolId) {
        return ResponseEntity.ok(service.listarPorRol(rolId).stream().map(mapper::toResponse).toList());
    }

    @PostMapping({"", "/"})
    public ResponseEntity<RolPermisoResponse> create(@RequestBody RolPermisoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(service.asignar(request.getRolId(), request.getPermisoId())));
    }

    @DeleteMapping("/rol/{rolId}/permiso/{permisoId}")
    public ResponseEntity<Void> delete(@PathVariable Long rolId, @PathVariable Long permisoId) {
        service.quitar(rolId, permisoId);
        return ResponseEntity.noContent().build();
    }
}
