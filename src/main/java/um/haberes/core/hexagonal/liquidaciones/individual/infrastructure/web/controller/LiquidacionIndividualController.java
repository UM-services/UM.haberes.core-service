package um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.individual.application.service.LiquidacionIndividualService;
import um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.dto.FichaLiquidacionIndividualResponse;
import um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.dto.LiquidarIndividualRequest;
import um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.dto.NovedadRegistradaResponse;
import um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.dto.RegistrarNovedadIndividualRequest;
import um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.dto.ResumenGeneracionCargosResponse;
import um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.dto.ResultadoProcesoResponse;
import um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.mapper.LiquidacionIndividualDtoMapper;

/**
 * Operaciones de liquidación de un legajo y período.
 *
 * <p>Namespace aditivo: los endpoints legacy de {@code /makeLiquidacion} que consume el cliente de
 * escritorio no se modifican.
 */
@RestController
@RequestMapping("/api/haberes/core/liquidacion-individual")
@RequiredArgsConstructor
public class LiquidacionIndividualController {

    private final LiquidacionIndividualService liquidacionIndividualService;

    private final LiquidacionIndividualDtoMapper dtoMapper;

    @GetMapping("/{legajoId}/{anho}/{mes}")
    public ResponseEntity<FichaLiquidacionIndividualResponse> ficha(@PathVariable Long legajoId,
                                                                   @PathVariable Integer anho,
                                                                   @PathVariable Integer mes) {
        return liquidacionIndividualService.obtenerFicha(legajoId, anho, mes)
                .map(ficha -> ResponseEntity.ok(dtoMapper.toResponse(ficha)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe el legajo " + legajoId));
    }

    @PostMapping("/{legajoId}/{anho}/{mes}/cargos")
    public ResponseEntity<ResumenGeneracionCargosResponse> generarCargos(@PathVariable Long legajoId,
                                                                        @PathVariable Integer anho,
                                                                        @PathVariable Integer mes) {
        return ResponseEntity.ok(dtoMapper.toResponse(
                liquidacionIndividualService.generarCargos(legajoId, anho, mes)));
    }

    @PostMapping("/{legajoId}/{anho}/{mes}/liquidar")
    public ResponseEntity<ResultadoProcesoResponse> liquidar(@PathVariable Long legajoId,
                                                             @PathVariable Integer anho,
                                                             @PathVariable Integer mes,
                                                             @RequestBody(required = false)
                                                             LiquidarIndividualRequest request) {
        boolean forzar = request != null && Boolean.TRUE.equals(request.getForzar());
        return ResponseEntity.ok(dtoMapper.toResponse(
                liquidacionIndividualService.liquidar(legajoId, anho, mes, forzar)));
    }

    @DeleteMapping("/{legajoId}/{anho}/{mes}/{forzar}")
    public ResponseEntity<ResultadoProcesoResponse> eliminarLiquidacion(@PathVariable Long legajoId,
                                                                       @PathVariable Integer anho,
                                                                       @PathVariable Integer mes,
                                                                       @PathVariable Boolean forzar) {
        return ResponseEntity.ok(dtoMapper.toResponse(
                liquidacionIndividualService.eliminarLiquidacion(legajoId, anho, mes, forzar)));
    }

    @PostMapping("/{legajoId}/{anho}/{mes}/novedad")
    public ResponseEntity<NovedadRegistradaResponse> registrarNovedad(@PathVariable Long legajoId,
                                                                     @PathVariable Integer anho,
                                                                     @PathVariable Integer mes,
                                                                     @Valid @RequestBody
                                                                     RegistrarNovedadIndividualRequest request) {
        return ResponseEntity.ok(dtoMapper.toResponse(liquidacionIndividualService.registrarNovedad(
                legajoId, anho, mes, dtoMapper.toDomain(request))));
    }

    @DeleteMapping("/{legajoId}/{anho}/{mes}/novedad/{novedadId}/{forzar}")
    public ResponseEntity<ResultadoProcesoResponse> eliminarNovedad(@PathVariable Long legajoId,
                                                                   @PathVariable Integer anho,
                                                                   @PathVariable Integer mes,
                                                                   @PathVariable Long novedadId,
                                                                   @PathVariable Boolean forzar) {
        return ResponseEntity.ok(dtoMapper.toResponse(liquidacionIndividualService.eliminarNovedad(
                legajoId, anho, mes, novedadId, forzar)));
    }
}
