package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.hora_escala.application.exception.EscalaHoraEtecException;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.exception.CargoLiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.ResultadoGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GenerarCargosEtecUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GenerarCargosLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.web.dto.CargoLiquidacionResponse;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.web.mapper.CargoLiquidacionDtoMapper;

/**
 * Generación de cargos de liquidación por contexto.
 *
 * <p>Los endpoints de {@code generacion/legajo} regeneran todos los universos del legajo; los de
 * {@code generacion/etec} sólo el universo ETEC (legajo o período completo) y son los que consume
 * {@code haberes-etec}. La previsualización no escribe nada.</p>
 */
@RestController
@RequestMapping("/api/haberes/core/cargoliquidacion/generacion")
@RequiredArgsConstructor
public class GeneracionCargosLiquidacionController {

    private final GenerarCargosLegajoUseCase generarCargosLegajoUseCase;

    private final GenerarCargosEtecUseCase generarCargosEtecUseCase;

    private final CargoLiquidacionDtoMapper cargoLiquidacionDtoMapper;

    @PostMapping("/legajo/{legajoId}/{anho}/{mes}")
    public ResponseEntity<List<CargoLiquidacionResponse>> generarByLegajo(@PathVariable Long legajoId,
                                                                         @PathVariable Integer anho,
                                                                         @PathVariable Integer mes) {
        return responder(generarCargosLegajoUseCase.generar(legajoId, anho, mes));
    }

    @PostMapping("/etec/legajo/{legajoId}/{anho}/{mes}")
    public ResponseEntity<List<CargoLiquidacionResponse>> generarEtecByLegajo(@PathVariable Long legajoId,
                                                                             @PathVariable Integer anho,
                                                                             @PathVariable Integer mes) {
        return responder(generarCargosEtecUseCase.generarByLegajo(legajoId, anho, mes));
    }

    @PostMapping("/etec/periodo/{anho}/{mes}")
    public ResponseEntity<List<CargoLiquidacionResponse>> generarEtecByPeriodo(@PathVariable Integer anho,
                                                                              @PathVariable Integer mes) {
        return responder(generarCargosEtecUseCase.generarByPeriodo(anho, mes));
    }

    @GetMapping("/preview/etec/legajo/{legajoId}/{anho}/{mes}")
    public ResponseEntity<List<CargoLiquidacionResponse>> previsualizarEtecByLegajo(@PathVariable Long legajoId,
                                                                                   @PathVariable Integer anho,
                                                                                   @PathVariable Integer mes) {
        return ResponseEntity.ok(toResponseList(
                    generarCargosEtecUseCase.previsualizarByLegajo(legajoId, anho, mes)));
    }

    @GetMapping("/preview/etec/periodo/{anho}/{mes}")
    public ResponseEntity<List<CargoLiquidacionResponse>> previsualizarEtecByPeriodo(@PathVariable Integer anho,
                                                                                    @PathVariable Integer mes) {
        return ResponseEntity.ok(toResponseList(generarCargosEtecUseCase.previsualizarByPeriodo(anho, mes)));
    }

    @ExceptionHandler({CargoLiquidacionException.class, EscalaHoraEtecException.class})
    public ProblemDetail errorDeNegocio(RuntimeException excepcion) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                excepcion.getMessage());
        problemDetail.setTitle("Generación de cargos rechazada");
        return problemDetail;
    }

    private ResponseEntity<List<CargoLiquidacionResponse>> responder(ResultadoGeneracionCargos resultado) {
        return new ResponseEntity<>(toResponseList(resultado.cargos()), HttpStatus.OK);
    }

    private List<CargoLiquidacionResponse> toResponseList(List<CargoLiquidacion> cargos) {
        return cargos.stream()
                .map(cargoLiquidacionDtoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
