package um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.web.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.exception.LiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.service.LiquidacionService;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.LiquidacionPeriodoForward;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.web.dto.LiquidacionPeriodoResponse;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.web.dto.LiquidacionRequest;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.web.dto.LiquidacionResponse;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.web.mapper.LiquidacionDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/liquidacion")
@RequiredArgsConstructor
public class LiquidacionController {

    private final LiquidacionService service;
    private final LiquidacionDtoMapper liquidacionDtoMapper;

    @GetMapping("/periodo/{anho}/{mes}/{limit}")
    public ResponseEntity<List<LiquidacionResponse>> findAllByPeriodo(@PathVariable Integer anho,
            @PathVariable Integer mes, @PathVariable Integer limit) {
        return ResponseEntity.ok(
                toResponseList(service.getLiquidacionesByPeriodo(anho, mes, limit)));
    }

    @GetMapping("/periodolegajo/{anho}/{mes}/{legajoId}/{limit}")
    public ResponseEntity<List<LiquidacionResponse>> findAllByPeriodoLegajo(@PathVariable Integer anho,
            @PathVariable Integer mes, @PathVariable Long legajoId, @PathVariable Integer limit) {
        return ResponseEntity.ok(
                toResponseList(service.getLiquidacionesByPeriodoLegajo(anho, mes, legajoId, limit)));
    }

    @GetMapping("/semestre/{anho}/{semestre}/{limit}")
    public ResponseEntity<List<LiquidacionResponse>> findAllBySemestre(@PathVariable Integer anho,
            @PathVariable Integer semestre, @PathVariable Integer limit) {
        return ResponseEntity.ok(
                toResponseList(service.getLiquidacionesBySemestre(anho, semestre, limit)));
    }

    @GetMapping("/semestrelegajo/{anho}/{semestre}/{legajoId}/{limit}")
    public ResponseEntity<List<LiquidacionResponse>> findAllBySemestreLegajo(@PathVariable Integer anho,
            @PathVariable Integer semestre, @PathVariable Long legajoId, @PathVariable Integer limit) {
        return ResponseEntity.ok(
                toResponseList(service.getLiquidacionesBySemestreLegajo(anho, semestre, legajoId, limit)));
    }

    @GetMapping("/legajo/{legajoId}")
    public ResponseEntity<List<LiquidacionResponse>> findAllByLegajo(@PathVariable Long legajoId) {
        return ResponseEntity.ok(toResponseList(service.getLiquidacionesByLegajo(legajoId)));
    }

    @GetMapping("/legajoforward/{legajoId}/{anho}/{mes}")
    public ResponseEntity<List<LiquidacionPeriodoResponse>> findAllByLegajoForward(@PathVariable Long legajoId,
            @PathVariable Integer anho, @PathVariable Integer mes) {
        return ResponseEntity.ok(
                toPeriodoResponseList(service.getLiquidacionesByLegajoForward(legajoId, anho, mes)));
    }

    @GetMapping("/dependencia/{dependenciaId}/{anho}/{mes}/{salida}")
    public ResponseEntity<List<LiquidacionResponse>> findAllByDependencia(@PathVariable Integer dependenciaId,
            @PathVariable Integer anho, @PathVariable Integer mes, @PathVariable String salida) {
        return ResponseEntity.ok(
                toResponseList(service.getLiquidacionesByDependencia(dependenciaId, anho, mes, salida)));
    }

    @GetMapping("/acreditado/{anho}/{mes}")
    public ResponseEntity<List<LiquidacionResponse>> findAllByAcreditado(@PathVariable Integer anho,
            @PathVariable Integer mes) {
        return ResponseEntity.ok(toResponseList(service.getLiquidacionesByAcreditado(anho, mes)));
    }

    @GetMapping("/{liquidacionId}")
    public ResponseEntity<LiquidacionResponse> findByLiquidacionId(@PathVariable Long liquidacionId) {
        try {
            return ResponseEntity.ok(liquidacionDtoMapper.toResponse(service.getLiquidacionById(liquidacionId)));
        } catch (LiquidacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/unique/{legajoId}/{anho}/{mes}")
    public ResponseEntity<LiquidacionResponse> findByUnique(@PathVariable Long legajoId,
            @PathVariable Integer anho, @PathVariable Integer mes) {
        try {
            return ResponseEntity.ok(
                    liquidacionDtoMapper.toResponse(service.getLiquidacionByUniqueKey(legajoId, anho, mes)));
        } catch (LiquidacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<LiquidacionResponse> add(@Valid @RequestBody LiquidacionRequest request) {
        Liquidacion created = service.add(liquidacionDtoMapper.toDomain(request));
        return new ResponseEntity<>(liquidacionDtoMapper.toResponse(created), HttpStatus.CREATED);
    }

    @PostMapping("/version/{version}")
    public ResponseEntity<LiquidacionResponse> addVersion(@Valid @RequestBody LiquidacionRequest request,
            @PathVariable Integer version) {
        Liquidacion created = service.addVersion(liquidacionDtoMapper.toDomain(request), version);
        return new ResponseEntity<>(liquidacionDtoMapper.toResponse(created), HttpStatus.CREATED);
    }

    @PostMapping("/acreditado/{fecha}")
    public ResponseEntity<LiquidacionResponse> acreditado(@Valid @RequestBody LiquidacionRequest request,
            @PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fecha) {
        try {
            Liquidacion acreditado = service.acreditado(liquidacionDtoMapper.toDomain(request), fecha);
            return new ResponseEntity<>(liquidacionDtoMapper.toResponse(acreditado), HttpStatus.CREATED);
        } catch (LiquidacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{liquidacionId}")
    public ResponseEntity<LiquidacionResponse> update(@Valid @RequestBody LiquidacionRequest request,
            @PathVariable Long liquidacionId) {
        try {
            Liquidacion updated = service.update(liquidacionDtoMapper.toDomain(request), liquidacionId);
            return ResponseEntity.ok(liquidacionDtoMapper.toResponse(updated));
        } catch (LiquidacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/saveall/{version}")
    public ResponseEntity<List<LiquidacionResponse>> saveall(@Valid @RequestBody List<LiquidacionRequest> requests,
            @PathVariable Integer version) {
        List<Liquidacion> liquidaciones = requests.stream()
                .map(liquidacionDtoMapper::toDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(toResponseList(service.saveAll(liquidaciones, version)));
    }

    @PutMapping("/version/{liquidacionId}/{version}")
    public ResponseEntity<LiquidacionResponse> updateVersion(@Valid @RequestBody LiquidacionRequest request,
            @PathVariable Long liquidacionId, @PathVariable Integer version) {
        try {
            Liquidacion updated = service.updateVersion(liquidacionDtoMapper.toDomain(request), liquidacionId,
                    version);
            return ResponseEntity.ok(liquidacionDtoMapper.toResponse(updated));
        } catch (LiquidacionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/periodo/{anho}/{mes}")
    public ResponseEntity<Void> deleteByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
        service.deleteByPeriodo(anho, mes);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private List<LiquidacionResponse> toResponseList(List<Liquidacion> liquidaciones) {
        return liquidaciones.stream()
                .map(liquidacionDtoMapper::toResponse)
                .collect(Collectors.toList());
    }

    private List<LiquidacionPeriodoResponse> toPeriodoResponseList(List<LiquidacionPeriodoForward> liquidaciones) {
        return liquidaciones.stream()
                .map(liquidacionDtoMapper::toPeriodoResponse)
                .collect(Collectors.toList());
    }
}
