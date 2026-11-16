package um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.general.application.service.LiquidacionGeneralService;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.EstadoLegajoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.InicioProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.dto.InicioProcesoGeneralResponse;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.dto.PadronLiquidacionGeneralResponse;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.dto.ProcesoGeneralRequest;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.dto.ProcesoGeneralResponse;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.dto.ResultadoLegajoGeneralResponse;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.mapper.LiquidacionGeneralDtoMapper;

/**
 * Liquidación general: padrón agregado del período y procesos masivos asíncronos.
 *
 * <p>Namespace aditivo: los endpoints legacy de {@code /makeLiquidacion} y
 * {@code /makeLiquidacionGeneral} que consume el cliente de escritorio no se modifican.</p>
 */
@RestController
@RequestMapping("/api/haberes/core/liquidacion-general")
@RequiredArgsConstructor
public class LiquidacionGeneralController {

    private final LiquidacionGeneralService liquidacionGeneralService;

    private final LiquidacionGeneralDtoMapper dtoMapper;

    @GetMapping("/{anho}/{mes}")
    public PadronLiquidacionGeneralResponse padron(@PathVariable Integer anho, @PathVariable Integer mes) {
        return dtoMapper.toResponse(liquidacionGeneralService.obtener(anho, mes));
    }

    @PostMapping("/{anho}/{mes}/cargos")
    public ResponseEntity<InicioProcesoGeneralResponse> generarCargos(
            @PathVariable Integer anho, @PathVariable Integer mes,
            @RequestHeader(value = "X-Legajo-Solicitante", required = false) Long legajoSolicitante) {
        return responder(liquidacionGeneralService.iniciar(anho, mes, legajoSolicitante));
    }

    @PostMapping("/{anho}/{mes}/liquidar")
    public ResponseEntity<InicioProcesoGeneralResponse> liquidar(
            @PathVariable Integer anho, @PathVariable Integer mes,
            @RequestBody(required = false) ProcesoGeneralRequest request,
            @RequestHeader(value = "X-Legajo-Solicitante", required = false) Long legajoSolicitante) {
        return responder(liquidacionGeneralService.iniciar(anho, mes, forzar(request), legajoSolicitante));
    }

    @PostMapping("/{anho}/{mes}/eliminar")
    public ResponseEntity<InicioProcesoGeneralResponse> eliminar(
            @PathVariable Integer anho, @PathVariable Integer mes,
            @RequestBody(required = false) ProcesoGeneralRequest request,
            @RequestHeader(value = "X-Legajo-Solicitante", required = false) Long legajoSolicitante) {
        return responder(liquidacionGeneralService.eliminar(anho, mes, forzar(request), legajoSolicitante));
    }

    @GetMapping("/proceso/{procesoId}")
    public ProcesoGeneralResponse proceso(@PathVariable String procesoId) {
        return liquidacionGeneralService.obtener(procesoId)
                .map(dtoMapper::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe el proceso " + procesoId));
    }

    @GetMapping("/proceso/{procesoId}/detalle/{resultado}")
    public List<ResultadoLegajoGeneralResponse> detalle(@PathVariable String procesoId,
                                                        @PathVariable EstadoLegajoGeneral resultado) {
        if (liquidacionGeneralService.obtener(procesoId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el proceso " + procesoId);
        }
        return liquidacionGeneralService.obtener(procesoId, resultado).stream()
                .map(dtoMapper::toResponse)
                .toList();
    }

    @GetMapping("/procesos/{anho}/{mes}")
    public List<ProcesoGeneralResponse> procesos(@PathVariable Integer anho, @PathVariable Integer mes) {
        return liquidacionGeneralService.listar(anho, mes).stream()
                .map(dtoMapper::toResponse)
                .toList();
    }

    private ResponseEntity<InicioProcesoGeneralResponse> responder(InicioProcesoGeneral inicio) {
        if (inicio.iniciadoProceso()) {
            return ResponseEntity.accepted().body(dtoMapper.toResponse(inicio));
        }
        if (LiquidacionGeneralService.PROCESO_EN_CURSO.equals(inicio.motivo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(dtoMapper.toResponse(inicio));
        }
        return ResponseEntity.ok(dtoMapper.toResponse(inicio));
    }

    private boolean forzar(ProcesoGeneralRequest request) {
        return request != null && Boolean.TRUE.equals(request.getForzar());
    }
}
