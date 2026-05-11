package um.haberes.core.hexagonal.geografica.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import um.haberes.core.exception.GeograficaException;
import um.haberes.core.hexagonal.geografica.application.service.GeograficaService;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.infrastructure.web.dto.GeograficaRequest;
import um.haberes.core.hexagonal.geografica.infrastructure.web.dto.GeograficaResponse;
import um.haberes.core.hexagonal.geografica.infrastructure.web.mapper.GeograficaDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/api/haberes/core/geografica", "/geografica"})
@RequiredArgsConstructor
public class GeograficaController {

    private final GeograficaService geograficaService;
    private final GeograficaDtoMapper geograficaDtoMapper;

    @GetMapping("/{geograficaId}")
    public ResponseEntity<GeograficaResponse> getGeograficaById(@PathVariable Integer geograficaId) {
        try {
            return ResponseEntity.ok(geograficaDtoMapper.toResponse(geograficaService.getGeograficaById(geograficaId)));
        } catch (GeograficaException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<GeograficaResponse>> getAllGeograficas() {
        List<GeograficaResponse> responses = geograficaService.getAllGeograficas().stream()
                .map(geograficaDtoMapper::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("/{geograficaId}")
    public ResponseEntity<GeograficaResponse> updateGeografica(@PathVariable Integer geograficaId, @RequestBody GeograficaRequest request) {
        Geografica domain = geograficaDtoMapper.toDomain(request);
        try {
            return ResponseEntity.ok(geograficaDtoMapper.toResponse(geograficaService.updateGeografica(geograficaId, domain)));
        } catch (GeograficaException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
