package um.haberes.core.hexagonal.geografica.infrastructure.web.controller;

import um.haberes.core.hexagonal.geografica.application.exception.GeograficaException;
import um.haberes.core.hexagonal.geografica.application.service.GeograficaService;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.infrastructure.web.dto.GeograficaRequest;
import um.haberes.core.hexagonal.geografica.infrastructure.web.dto.GeograficaResponse;
import um.haberes.core.hexagonal.geografica.infrastructure.web.mapper.GeograficaDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/api/haberes/core/geografica", "/geografica"})
@RequiredArgsConstructor
public class GeograficaController {

    private final GeograficaService geograficaService;
    private final GeograficaDtoMapper geograficaDtoMapper;

    @PostMapping
    public ResponseEntity<GeograficaResponse> createGeografica(@Valid @RequestBody GeograficaRequest request) {
        Geografica domain = geograficaDtoMapper.toDomain(request);
        Geografica created = geograficaService.createGeografica(domain);
        return new ResponseEntity<>(geograficaDtoMapper.toResponse(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeograficaResponse> getGeograficaById(@PathVariable Integer id) {
        try {
            Geografica geografica = geograficaService.getGeograficaById(id);
            return ResponseEntity.ok(geograficaDtoMapper.toResponse(geografica));
        } catch (GeograficaException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<GeograficaResponse>> getAllGeograficas() {
        List<GeograficaResponse> responses = geograficaService.getAllGeograficas().stream()
                .map(geograficaDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/ids")
    public ResponseEntity<List<GeograficaResponse>> getGeograficasByIds(@RequestBody List<Integer> ids) {
        List<GeograficaResponse> responses = geograficaService.getGeograficasByIds(ids).stream()
                .map(geograficaDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeograficaResponse> updateGeografica(@PathVariable Integer id, @Valid @RequestBody GeograficaRequest request) {
        Geografica domain = geograficaDtoMapper.toDomain(request);
        try {
            Geografica updated = geograficaService.updateGeografica(id, domain);
            return ResponseEntity.ok(geograficaDtoMapper.toResponse(updated));
        } catch (GeograficaException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGeografica(@PathVariable Integer id) {
        geograficaService.deleteGeografica(id);
        return ResponseEntity.noContent().build();
    }
}
