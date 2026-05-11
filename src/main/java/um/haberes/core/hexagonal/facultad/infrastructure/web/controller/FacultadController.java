package um.haberes.core.hexagonal.facultad.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import um.haberes.core.exception.FacultadException;
import um.haberes.core.hexagonal.facultad.application.service.FacultadService;
import um.haberes.core.hexagonal.facultad.infrastructure.web.dto.FacultadResponse;
import um.haberes.core.hexagonal.facultad.infrastructure.web.mapper.FacultadDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/api/haberes/core/facultad", "/facultad"})
@RequiredArgsConstructor
public class FacultadController {

    private final FacultadService facultadService;
    private final FacultadDtoMapper facultadDtoMapper;

    @GetMapping("/{facultadId}")
    public ResponseEntity<FacultadResponse> getFacultadById(@PathVariable Integer facultadId) {
        try {
            return ResponseEntity.ok(facultadDtoMapper.toResponse(facultadService.getFacultadById(facultadId)));
        } catch (FacultadException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<FacultadResponse>> getAllFacultades() {
        List<FacultadResponse> responses = facultadService.getAllFacultades().stream()
                .map(facultadDtoMapper::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/facultades")
    public ResponseEntity<List<FacultadResponse>> getFacultadesEstaticas() {
        List<FacultadResponse> responses = facultadService.getFacultadesEstaticas().stream()
                .map(facultadDtoMapper::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
