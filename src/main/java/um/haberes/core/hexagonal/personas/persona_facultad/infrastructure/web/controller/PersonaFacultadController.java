package um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.web.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import um.haberes.core.hexagonal.personas.persona_facultad.application.exception.PersonaFacultadException;
import um.haberes.core.hexagonal.personas.persona_facultad.application.service.PersonaFacultadService;
import um.haberes.core.hexagonal.personas.persona_facultad.domain.model.PersonaFacultad;
import um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.web.dto.PersonaFacultadRequest;
import um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.web.dto.PersonaFacultadResponse;
import um.haberes.core.hexagonal.personas.persona_facultad.infrastructure.web.mapper.PersonaFacultadDtoMapper;

/**
 * Facultades habilitadas de un agente.
 *
 * El cliente legacy {@code bonos.vb6} ({@code frmAsigCargo}) sigue usando estas URLs y
 * parsea el JSON campo por campo: el contrato está fijado por
 * {@code PersonaFacultadLegacyContractTest} y no debe cambiarse sin actualizar el VB6.
 */
@RestController
@RequestMapping("/api/haberes/core/personaFacultad")
@RequiredArgsConstructor
public class PersonaFacultadController {

    private final PersonaFacultadService service;

    private final PersonaFacultadDtoMapper dtoMapper;

    @GetMapping("/facultad/{facultadId}")
    public ResponseEntity<List<PersonaFacultadResponse>> findAllByFacultad(@PathVariable Integer facultadId) {
        return ResponseEntity.ok(toResponses(service.findAllByFacultad(facultadId)));
    }

    @GetMapping("/persona/{legajoId}")
    public ResponseEntity<List<PersonaFacultadResponse>> findAllByPersona(@PathVariable Long legajoId) {
        return ResponseEntity.ok(toResponses(service.findAllByPersona(legajoId)));
    }

    @PostMapping("/")
    public ResponseEntity<PersonaFacultadResponse> add(@Valid @RequestBody PersonaFacultadRequest request) {
        try {
            return ResponseEntity.ok(dtoMapper.toResponse(service.add(dtoMapper.toDomain(request))));
        } catch (PersonaFacultadException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/unique/{legajoId}/{facultadId}")
    public ResponseEntity<Void> deleteByUnique(@PathVariable Long legajoId, @PathVariable Integer facultadId) {
        service.deleteByUnique(legajoId, facultadId);
        return ResponseEntity.noContent().build();
    }

    private List<PersonaFacultadResponse> toResponses(List<PersonaFacultad> personaFacultades) {
        return personaFacultades.stream()
                .map(dtoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
