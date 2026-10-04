package um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.infrastructure.web.controller;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.application.exception.AcreditacionPagoException;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.application.service.AcreditacionPagoService;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.model.AcreditacionPago;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.infrastructure.web.dto.AcreditacionPagoRequest;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.infrastructure.web.dto.AcreditacionPagoResponse;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.infrastructure.web.mapper.AcreditacionPagoDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/acreditacionpago")
@RequiredArgsConstructor
public class AcreditacionPagoController {

    private final AcreditacionPagoService acreditacionPagoService;
    private final AcreditacionPagoDtoMapper acreditacionPagoDtoMapper;

    @GetMapping("/unique/{anho}/{mes}/{fechapago}")
    public ResponseEntity<AcreditacionPagoResponse> findByUnique(@PathVariable Integer anho, @PathVariable Integer mes,
            @PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fechapago) {
        try {
            AcreditacionPago acreditacionPago = acreditacionPagoService.findByUnique(anho, mes, fechapago);
            return ResponseEntity.ok(acreditacionPagoDtoMapper.toResponse(acreditacionPago));
        } catch (AcreditacionPagoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<AcreditacionPagoResponse> add(@Valid @RequestBody AcreditacionPagoRequest request) {
        AcreditacionPago created = acreditacionPagoService.add(acreditacionPagoDtoMapper.toDomain(request));
        return new ResponseEntity<>(acreditacionPagoDtoMapper.toResponse(created), HttpStatus.OK);
    }

    @PutMapping("/{acreditacionpagoId}")
    public ResponseEntity<AcreditacionPagoResponse> update(@Valid @RequestBody AcreditacionPagoRequest request,
            @PathVariable Long acreditacionpagoId) {
        try {
            AcreditacionPago updated = acreditacionPagoService.update(acreditacionPagoDtoMapper.toDomain(request),
                    acreditacionpagoId);
            return ResponseEntity.ok(acreditacionPagoDtoMapper.toResponse(updated));
        } catch (AcreditacionPagoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
