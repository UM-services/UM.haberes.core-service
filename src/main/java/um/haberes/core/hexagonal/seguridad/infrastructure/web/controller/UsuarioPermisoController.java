package um.haberes.core.hexagonal.seguridad.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.haberes.core.hexagonal.seguridad.application.service.UsuarioPermisoService;
import um.haberes.core.hexagonal.seguridad.domain.model.UsuarioPermiso;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.UsuarioPermisoRequest;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.UsuarioPermisoResponse;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.mapper.SeguridadDtoMapper;

import java.util.List;

@RestController
@RequestMapping("/api/haberes/core/usuarioPermiso")
@RequiredArgsConstructor
public class UsuarioPermisoController {

    private final UsuarioPermisoService service;
    private final SeguridadDtoMapper mapper;

    @GetMapping("/usuario/{legajoId}")
    public ResponseEntity<List<UsuarioPermisoResponse>> findByLegajo(@PathVariable Long legajoId) {
        return ResponseEntity.ok(service.listarPorLegajo(legajoId).stream().map(mapper::toResponse).toList());
    }

    @PutMapping("/usuario/{legajoId}/permiso/{permisoId}")
    public ResponseEntity<UsuarioPermisoResponse> set(@PathVariable Long legajoId,
                                                      @PathVariable Long permisoId,
                                                      @RequestBody UsuarioPermisoRequest request) {
        UsuarioPermiso guardado = service.set(legajoId, permisoId,
                request == null ? null : request.getOtorgado());
        return guardado == null
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(mapper.toResponse(guardado));
    }

    @DeleteMapping("/usuario/{legajoId}/permiso/{permisoId}")
    public ResponseEntity<Void> delete(@PathVariable Long legajoId, @PathVariable Long permisoId) {
        service.eliminar(legajoId, permisoId);
        return ResponseEntity.noContent().build();
    }
}
