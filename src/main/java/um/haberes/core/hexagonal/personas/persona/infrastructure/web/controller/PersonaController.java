package um.haberes.core.hexagonal.personas.persona.infrastructure.web.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import lombok.extern.slf4j.Slf4j;
import um.haberes.core.hexagonal.personas.persona.application.exception.PersonaException;
import um.haberes.core.hexagonal.personas.persona.application.service.PersonaService;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.dto.PersonaRequest;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.dto.PersonaResponse;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.dto.PersonaSearchResponse;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.dto.PersonaUploadRequest;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.mapper.PersonaDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/persona")
@RequiredArgsConstructor
@Slf4j
public class PersonaController {

    private final PersonaService service;

    private final PersonaDtoMapper dtoMapper;

    @GetMapping("/")
    public ResponseEntity<List<PersonaResponse>> findAll() {
        return ResponseEntity.ok(toResponses(service.findAll()));
    }

    @PostMapping("/legajos")
    public ResponseEntity<List<PersonaResponse>> findAllLegajos(@RequestBody List<Long> legajos) {
        return ResponseEntity.ok(toResponses(service.findAllLegajos(legajos)));
    }

    // Legajos que tienen cargo en cursos para el período solicitado
    @GetMapping("/docente/{anho}/{mes}")
    public ResponseEntity<List<PersonaResponse>> findAllDocente(@PathVariable Integer anho,
                                                                @PathVariable Integer mes) {
        return ResponseEntity.ok(toResponses(service.findAllDocente(anho, mes)));
    }

    @GetMapping("/nodocente/{anho}/{mes}")
    public ResponseEntity<List<PersonaResponse>> findAllNoDocente(@PathVariable Integer anho,
                                                                  @PathVariable Integer mes) {
        return ResponseEntity.ok(toResponses(service.findAllNoDocente(anho, mes)));
    }

    @GetMapping("/semestre/{anho}/{semestre}")
    public ResponseEntity<List<PersonaResponse>> findAllBySemestre(@PathVariable Integer anho,
                                                                   @PathVariable Integer semestre) {
        return ResponseEntity.ok(toResponses(service.findAllBySemestre(anho, semestre)));
    }

    @GetMapping("/desarraigo/{anho}/{mes}")
    public ResponseEntity<List<PersonaResponse>> findAllByDesarraigo(@PathVariable Integer anho,
                                                                     @PathVariable Integer mes) {
        return ResponseEntity.ok(toResponses(service.findAllByDesarraigo(anho, mes)));
    }

    @GetMapping("/liquidables")
    public ResponseEntity<List<PersonaResponse>> findAllLiquidables() {
        return ResponseEntity.ok(toResponses(service.findAllLiquidables()));
    }

    @GetMapping("/facultad/{facultadId}")
    public ResponseEntity<List<PersonaResponse>> findAllByFacultad(@PathVariable Integer facultadId) {
        return ResponseEntity.ok(toResponses(service.findAllByFacultad(facultadId)));
    }

    @PostMapping("/search")
    public ResponseEntity<List<PersonaSearchResponse>> findByStrings(@RequestBody List<String> conditions) {
        return ResponseEntity.ok(service.findByStrings(conditions).stream()
                .map(dtoMapper::toSearchResponse)
                .toList());
    }

    @GetMapping("/{legajoId}")
    public ResponseEntity<PersonaResponse> findByLegajoId(@PathVariable Long legajoId) {
        try {
            return ResponseEntity.ok(dtoMapper.toResponse(service.findByLegajoId(legajoId)));
        } catch (PersonaException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<PersonaResponse> findByDocumento(@PathVariable BigDecimal documento) {
        try {
            return ResponseEntity.ok(dtoMapper.toResponse(service.findByDocumento(documento)));
        } catch (PersonaException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<PersonaResponse> add(@Valid @RequestBody PersonaRequest request) {
        Persona persona = dtoMapper.toDomain(request);
        return ResponseEntity.ok(dtoMapper.toResponse(service.add(persona)));
    }

    @PutMapping("/{legajoId}")
    public ResponseEntity<PersonaResponse> update(@Valid @RequestBody PersonaRequest request,
                                                  @PathVariable Long legajoId) {
        Persona persona = dtoMapper.toDomain(request);
        return ResponseEntity.ok(dtoMapper.toResponse(service.update(persona, legajoId)));
    }

    @PutMapping("/")
    public ResponseEntity<List<PersonaResponse>> saveAll(@RequestBody List<PersonaRequest> requests) {
        List<Persona> personas = requests.stream()
                .map(dtoMapper::toDomain)
                .toList();
        return ResponseEntity.ok(toResponses(service.saveall(personas)));
    }

    @PostMapping("/upload")
    public ResponseEntity<List<PersonaResponse>> upload(@Valid @RequestBody PersonaUploadRequest request) {
        log.debug("Upload - Controller");
        return ResponseEntity.ok(toResponses(service.upload(dtoMapper.toUploadedFile(request))));
    }

    private List<PersonaResponse> toResponses(List<Persona> personas) {
        return personas.stream()
                .map(dtoMapper::toResponse)
                .toList();
    }
}
