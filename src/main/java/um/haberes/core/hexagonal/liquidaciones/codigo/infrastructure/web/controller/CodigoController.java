package um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.web.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import um.haberes.core.hexagonal.liquidaciones.codigo.application.exception.CodigoException;
import um.haberes.core.hexagonal.liquidaciones.codigo.application.service.CodigoService;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.web.dto.CodigoRequest;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.web.dto.CodigoResponse;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.web.dto.CodigoSearchResponse;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.web.mapper.CodigoDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/codigo")
@RequiredArgsConstructor
public class CodigoController {

    private final CodigoService service;
    private final CodigoDtoMapper codigoDtoMapper;

    @GetMapping("/")
    public ResponseEntity<List<CodigoResponse>> findAll() {
        return ResponseEntity.ok(toResponseList(service.findAll()));
    }

    @GetMapping("/periodo/{anho}/{mes}")
    public ResponseEntity<List<CodigoResponse>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
        return ResponseEntity.ok(toResponseList(service.findAllByPeriodo(anho, mes)));
    }

    @GetMapping("/search/{chain}")
    public ResponseEntity<List<CodigoSearchResponse>> findAllSearch(@PathVariable String chain) {
        return ResponseEntity.ok(service.findAllSearch(chain).stream()
                .map(codigoDtoMapper::toSearchResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{codigoId}")
    public ResponseEntity<CodigoResponse> findByCodigoId(@PathVariable Integer codigoId) {
        try {
            return ResponseEntity.ok(codigoDtoMapper.toResponse(service.findByCodigoId(codigoId)));
        } catch (CodigoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/last")
    public ResponseEntity<CodigoResponse> findLast() {
        try {
            return ResponseEntity.ok(codigoDtoMapper.toResponse(service.findLast()));
        } catch (CodigoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{codigoId}")
    public ResponseEntity<Void> delete(@PathVariable Integer codigoId) {
        service.delete(codigoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/")
    public ResponseEntity<CodigoResponse> add(@Valid @RequestBody CodigoRequest request) {
        Codigo created = service.add(codigoDtoMapper.toDomain(request));
        return new ResponseEntity<>(codigoDtoMapper.toResponse(created), HttpStatus.CREATED);
    }

    @PutMapping("/{codigoId}")
    public ResponseEntity<CodigoResponse> update(@Valid @RequestBody CodigoRequest request, @PathVariable Integer codigoId) {
        try {
            Codigo updated = service.update(codigoDtoMapper.toDomain(request), codigoId);
            return ResponseEntity.ok(codigoDtoMapper.toResponse(updated));
        } catch (CodigoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/")
    public ResponseEntity<List<CodigoResponse>> saveAll(@Valid @RequestBody List<CodigoRequest> requests) {
        List<Codigo> codigos = requests.stream()
                .map(codigoDtoMapper::toDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(toResponseList(service.saveAll(codigos)));
    }

    private List<CodigoResponse> toResponseList(List<Codigo> codigos) {
        return codigos.stream()
                .map(codigoDtoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
