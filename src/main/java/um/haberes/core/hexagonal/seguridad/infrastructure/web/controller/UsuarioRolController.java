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
import um.haberes.core.hexagonal.seguridad.application.service.UsuarioRolService;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.UsuarioRolRequest;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.UsuarioRolResponse;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.mapper.SeguridadDtoMapper;

import java.util.List;

@RestController
@RequestMapping("/api/haberes/core/usuarioRol")
@RequiredArgsConstructor
public class UsuarioRolController {

    private final UsuarioRolService service;
    private final SeguridadDtoMapper mapper;

    @GetMapping("/usuario/{legajoId}")
    public ResponseEntity<List<UsuarioRolResponse>> findByLegajo(@PathVariable Long legajoId) {
        return ResponseEntity.ok(service.listarPorLegajo(legajoId).stream().map(mapper::toResponse).toList());
    }

    @PostMapping({"", "/"})
    public ResponseEntity<UsuarioRolResponse> create(@RequestBody UsuarioRolRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(service.asignar(request.getLegajoId(), request.getRolId())));
    }

    @DeleteMapping("/usuario/{legajoId}/rol/{rolId}")
    public ResponseEntity<Void> delete(@PathVariable Long legajoId, @PathVariable Long rolId) {
        service.quitar(legajoId, rolId);
        return ResponseEntity.noContent().build();
    }
}
