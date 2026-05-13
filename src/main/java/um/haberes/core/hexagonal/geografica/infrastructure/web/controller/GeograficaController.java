package um.haberes.core.hexagonal.geografica.infrastructure.web.controller;

import um.haberes.core.hexagonal.geografica.application.service.GeograficaService;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.infrastructure.web.dto.GeograficaRequest;
import um.haberes.core.hexagonal.geografica.infrastructure.web.dto.GeograficaResponse;
import um.haberes.core.hexagonal.geografica.infrastructure.web.mapper.GeograficaDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/api/haberes/core/geografica", "/geografica"})
@RequiredArgsConstructor
public class GeograficaController {

    private final GeograficaService geograficaService;
    private final GeograficaDtoMapper geograficaDtoMapper;

    @GetMapping("/{id}")
    public ResponseEntity<GeograficaResponse> getGeograficaById(@PathVariable Integer id) {
        return geograficaService.getGeograficaById(id)
                .map(geografica -> new ResponseEntity<>(geograficaDtoMapper.toResponse(geografica), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/")
    public ResponseEntity<List<GeograficaResponse>> getAllGeograficas() {
        List<GeograficaResponse> responses = geograficaService.getAllGeograficas().stream()
                .map(geograficaDtoMapper::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping("/ids")
    public ResponseEntity<List<GeograficaResponse>> getGeograficasByIds(@RequestBody List<Integer> ids) {
        List<GeograficaResponse> responses = geograficaService.getGeograficasByIds(ids).stream()
                .map(geograficaDtoMapper::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeograficaResponse> updateGeografica(@PathVariable Integer id, @RequestBody GeograficaRequest request) {
        Geografica domain = geograficaDtoMapper.toDomain(request);
        return geograficaService.updateGeografica(id, domain)
                .map(updated -> new ResponseEntity<>(geograficaDtoMapper.toResponse(updated), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
