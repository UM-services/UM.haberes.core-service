package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.bono.application.exception.BonoException;
import um.haberes.core.hexagonal.liquidaciones.bono.application.service.BonoService;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.AuditoriaBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoImpresion;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.EnvioBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.IntegridadBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.PeriodoBono;
import um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.dto.ActividadResponse;
import um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.dto.AuditoriaBonoRequest;
import um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.dto.BonoImpresionResponse;
import um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.dto.IntegridadBonoResponse;
import um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.dto.SendBonoRequest;
import um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.mapper.BonoDtoMapper;
import um.haberes.core.util.ClientIpResolver;

@RestController
@RequestMapping("/api/haberes/core/bono")
@RequiredArgsConstructor
public class BonoIndividualController {

    static final String HEADER_LEGAJO_SOLICITANTE = "X-Legajo-Solicitante";

    private final BonoService bonoService;
    private final BonoDtoMapper bonoDtoMapper;
    private final ClientIpResolver clientIpResolver;

    @GetMapping("/{legajoId}/{anho}/{mes}/integridad")
    public ResponseEntity<IntegridadBonoResponse> integridad(@PathVariable Long legajoId, @PathVariable Integer anho,
            @PathVariable Integer mes) {
        IntegridadBono integridad = bonoService.verificarIntegridad(periodo(legajoId, anho, mes));
        return ResponseEntity.ok(bonoDtoMapper.toResponse(integridad));
    }

    @PostMapping("/{legajoId}/{anho}/{mes}/prepare")
    public ResponseEntity<ActividadResponse> prepare(@PathVariable Long legajoId, @PathVariable Integer anho,
            @PathVariable Integer mes) {
        try {
            return ResponseEntity.ok(bonoDtoMapper.toResponse(bonoService.preparar(periodo(legajoId, anho, mes))));
        } catch (BonoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/{legajoId}/{anho}/{mes}/auditoria")
    public ResponseEntity<BonoImpresionResponse> auditoria(@PathVariable Long legajoId, @PathVariable Integer anho,
            @PathVariable Integer mes, @RequestBody(required = false) AuditoriaBonoRequest request,
            HttpServletRequest httpRequest) {
        try {
            BonoImpresion impresion = bonoService.registrarAuditoria(
                    auditoria(legajoId, anho, mes, httpRequest, request == null ? null : request.getLegajoIdSolicitud()));
            return ResponseEntity.ok(bonoDtoMapper.toResponse(impresion));
        } catch (BonoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/{legajoId}/{anho}/{mes}/print-prepare")
    public ResponseEntity<?> printPrepare(@PathVariable Long legajoId, @PathVariable Integer anho,
            @PathVariable Integer mes, @RequestBody(required = false) AuditoriaBonoRequest request,
            HttpServletRequest httpRequest) {
        try {
            BonoImpresion impresion = bonoService.printPrepare(
                    auditoria(legajoId, anho, mes, httpRequest, request == null ? null : request.getLegajoIdSolicitud()));
            return ResponseEntity.ok(bonoDtoMapper.toResponse(impresion));
        } catch (BonoException e) {
            if (e.getFaltantes() != null) {
                return ResponseEntity.badRequest().body(IntegridadBonoResponse.builder()
                        .legajoId(legajoId)
                        .anho(anho)
                        .mes(mes)
                        .ok(false)
                        .faltantes(e.getFaltantes().stream().map(Enum::name).toList())
                        .build());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/{legajoId}/{anho}/{mes}/send-prepare")
    public ResponseEntity<BonoImpresionResponse> sendPrepare(@PathVariable Long legajoId, @PathVariable Integer anho,
            @PathVariable Integer mes, @Valid @RequestBody SendBonoRequest request, HttpServletRequest httpRequest) {
        try {
            EnvioBono envio = EnvioBono.builder()
                    .legajoId(legajoId)
                    .anho(anho)
                    .mes(mes)
                    .mailInstitucional(request.getMailInstitucional())
                    .legajoIdSolicitud(resolveLegajoIdSolicitante(httpRequest, request.getLegajoIdSolicitud()))
                    .ipAddress(clientIpResolver.resolve(httpRequest))
                    .build();
            return ResponseEntity.ok(bonoDtoMapper.toResponse(bonoService.prepararEnvio(envio)));
        } catch (BonoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{legajoId}/{anho}/{mes}/auditoria")
    public ResponseEntity<List<BonoImpresionResponse>> historialAuditoria(@PathVariable Long legajoId,
            @PathVariable Integer anho, @PathVariable Integer mes) {
        return ResponseEntity.ok(bonoDtoMapper
                .toImpresionResponseList(bonoService.getHistorial(periodo(legajoId, anho, mes))));
    }

    private PeriodoBono periodo(Long legajoId, Integer anho, Integer mes) {
        return PeriodoBono.builder()
                .legajoId(legajoId)
                .anho(anho)
                .mes(mes)
                .build();
    }

    private AuditoriaBono auditoria(Long legajoId, Integer anho, Integer mes, HttpServletRequest httpRequest,
            Long bodyLegajoIdSolicitud) {
        return AuditoriaBono.builder()
                .legajoId(legajoId)
                .anho(anho)
                .mes(mes)
                .legajoIdSolicitud(resolveLegajoIdSolicitante(httpRequest, bodyLegajoIdSolicitud))
                .ipAddress(clientIpResolver.resolve(httpRequest))
                .build();
    }

    private Long resolveLegajoIdSolicitante(HttpServletRequest httpRequest, Long bodyLegajoIdSolicitud) {
        String header = httpRequest.getHeader(HEADER_LEGAJO_SOLICITANTE);
        if (header != null && !header.isBlank()) {
            try {
                return Long.valueOf(header.trim());
            } catch (NumberFormatException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        HEADER_LEGAJO_SOLICITANTE + " header invalido: " + header);
            }
        }
        return bodyLegajoIdSolicitud;
    }
}
