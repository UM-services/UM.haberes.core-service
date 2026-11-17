package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.application.exception.ArchivoAcreditacionException;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.application.service.ArchivoAcreditacionService;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.ArchivoAcreditacion;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.dto.ArchivoAcreditacionRequest;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.dto.ArchivoAcreditacionResponse;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.mapper.ArchivoAcreditacionDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/acreditacion/archivo")
@RequiredArgsConstructor
public class ArchivoAcreditacionController {

    private final ArchivoAcreditacionService archivoAcreditacionService;
    private final ArchivoAcreditacionDtoMapper archivoAcreditacionDtoMapper;

    @PostMapping("/santander")
    public ResponseEntity<ArchivoAcreditacionResponse> generarSantander(
            @Valid @RequestBody ArchivoAcreditacionRequest request) {
        return generar(() -> archivoAcreditacionService.generarSantander(archivoAcreditacionDtoMapper.toDomain(request)));
    }

    @PostMapping("/otrosbancos")
    public ResponseEntity<ArchivoAcreditacionResponse> generarOtrosBancos(
            @Valid @RequestBody ArchivoAcreditacionRequest request) {
        return generar(
                () -> archivoAcreditacionService.generarOtrosBancos(archivoAcreditacionDtoMapper.toDomain(request)));
    }

    private ResponseEntity<ArchivoAcreditacionResponse> generar(java.util.function.Supplier<ArchivoAcreditacion> accion) {
        try {
            return ResponseEntity.ok(archivoAcreditacionDtoMapper.toResponse(accion.get()));
        } catch (ArchivoAcreditacionException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
