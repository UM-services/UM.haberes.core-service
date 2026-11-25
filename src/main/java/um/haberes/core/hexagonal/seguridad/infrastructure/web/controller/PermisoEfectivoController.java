package um.haberes.core.hexagonal.seguridad.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.haberes.core.hexagonal.seguridad.application.service.PermisoEfectivoService;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.PermisoEfectivoResponse;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.mapper.SeguridadDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/permisoEfectivo")
@RequiredArgsConstructor
public class PermisoEfectivoController {

    private final PermisoEfectivoService service;
    private final SeguridadDtoMapper mapper;

    @GetMapping("/usuario/{legajoId}")
    public ResponseEntity<PermisoEfectivoResponse> findByLegajo(@PathVariable Long legajoId) {
        return ResponseEntity.ok(mapper.toResponse(service.obtener(legajoId)));
    }
}
