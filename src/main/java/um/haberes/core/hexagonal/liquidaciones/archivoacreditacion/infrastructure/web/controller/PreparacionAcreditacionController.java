package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.application.exception.ArchivoAcreditacionException;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.application.service.ArchivoAcreditacionService;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.dto.PreparacionAcreditacionResponse;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.infrastructure.web.mapper.ArchivoAcreditacionDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/legajobanco/preparar")
@RequiredArgsConstructor
public class PreparacionAcreditacionController {

    private final ArchivoAcreditacionService archivoAcreditacionService;
    private final ArchivoAcreditacionDtoMapper archivoAcreditacionDtoMapper;

    @PostMapping("/{anho}/{mes}")
    public ResponseEntity<PreparacionAcreditacionResponse> prepararPeriodo(@PathVariable Integer anho,
            @PathVariable Integer mes) {
        try {
            return ResponseEntity
                    .ok(archivoAcreditacionDtoMapper.toResponse(archivoAcreditacionService.prepararPeriodo(anho, mes)));
        } catch (ArchivoAcreditacionException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/codigo/{anho}/{mes}/{codigoId}")
    public ResponseEntity<PreparacionAcreditacionResponse> prepararPorCodigo(@PathVariable Integer anho,
            @PathVariable Integer mes, @PathVariable Integer codigoId) {
        try {
            return ResponseEntity.ok(archivoAcreditacionDtoMapper
                    .toResponse(archivoAcreditacionService.prepararPorCodigo(anho, mes, codigoId)));
        } catch (ArchivoAcreditacionException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/{legajoId}/{anho}/{mes}")
    public ResponseEntity<PreparacionAcreditacionResponse> prepararLegajo(@PathVariable Long legajoId,
            @PathVariable Integer anho, @PathVariable Integer mes) {
        try {
            return ResponseEntity.ok(archivoAcreditacionDtoMapper
                    .toResponse(archivoAcreditacionService.prepararLegajo(legajoId, anho, mes)));
        } catch (ArchivoAcreditacionException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
