package um.haberes.core.hexagonal.liquidaciones.sijp.infrastructure.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.sijp.application.exception.SijpException;
import um.haberes.core.hexagonal.liquidaciones.sijp.application.exception.SijpSinControlException;
import um.haberes.core.hexagonal.liquidaciones.sijp.application.service.SijpService;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.VarianteSijp;
import um.haberes.core.hexagonal.liquidaciones.sijp.infrastructure.web.dto.SijpResponse;
import um.haberes.core.hexagonal.liquidaciones.sijp.infrastructure.web.mapper.SijpDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/sijp")
@RequiredArgsConstructor
public class SijpController {

    private final SijpService service;
    private final SijpDtoMapper mapper;

    @GetMapping("/{anho}/{mes}/{variante}")
    public ResponseEntity<SijpResponse> generar(@PathVariable Integer anho, @PathVariable Integer mes,
            @PathVariable String variante) {
        try {
            return ResponseEntity.ok(mapper.toResponse(service.generar(anho, mes, VarianteSijp.from(variante))));
        } catch (SijpSinControlException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (SijpException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
