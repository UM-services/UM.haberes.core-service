package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.bono.application.service.BonoService;
import um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.dto.BonoDependenciaPrintPrepareResponse;
import um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.mapper.BonoDependenciaDtoMapper;
import um.haberes.core.util.ClientIpResolver;

/**
 * Impresión masiva de bonos por dependencia. Prepara (recalcula actividad) y audita cada legajo
 * con la IP y el solicitante resueltos en el servidor, igual que el bono individual; el cliente
 * nunca envía una IP.
 */
@RestController
@RequestMapping("/api/haberes/core/bono/dependencia")
@RequiredArgsConstructor
public class BonoDependenciaController {

    /** Mismo header que usa {@link BonoIndividualController} para identificar al solicitante. */
    static final String HEADER_LEGAJO_SOLICITANTE = "X-Legajo-Solicitante";

    private final BonoService bonoService;
    private final BonoDependenciaDtoMapper mapper;
    private final ClientIpResolver clientIpResolver;

    @PostMapping("/{dependenciaId}/{anho}/{mes}/{salida}/print-prepare")
    public ResponseEntity<BonoDependenciaPrintPrepareResponse> printPrepare(
            @PathVariable Integer dependenciaId, @PathVariable Integer anho,
            @PathVariable Integer mes, @PathVariable String salida,
            HttpServletRequest httpRequest) {
        return ResponseEntity.ok(mapper.toPrintPrepareResponse(bonoService.prepararDependencia(
                dependenciaId, anho, mes, salida,
                resolveLegajoIdSolicitante(httpRequest), clientIpResolver.resolve(httpRequest))));
    }

    private Long resolveLegajoIdSolicitante(HttpServletRequest httpRequest) {
        String header = httpRequest.getHeader(HEADER_LEGAJO_SOLICITANTE);
        if (header == null || header.isBlank()) {
            return null;
        }
        try {
            return Long.valueOf(header.trim());
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    HEADER_LEGAJO_SOLICITANTE + " header invalido: " + header);
        }
    }
}
