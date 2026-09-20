package um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.web.controller;

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
import um.haberes.core.hexagonal.liquidaciones.letra.application.exception.LetraException;
import um.haberes.core.hexagonal.liquidaciones.letra.application.service.LetraService;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.model.Letra;
import um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.web.dto.LetraRequest;
import um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.web.dto.LetraResponse;
import um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.web.mapper.LetraDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/letra")
@RequiredArgsConstructor
public class LetraController {

    private static final int DEFAULT_LIMIT = 30000;

    private final LetraService service;
    private final LetraDtoMapper letraDtoMapper;

    @GetMapping("/periodo/{anho}/{mes}/{limit}")
    public ResponseEntity<List<LetraResponse>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes,
            @PathVariable Integer limit) {
        if (limit == 0) {
            limit = DEFAULT_LIMIT;
        }
        return ResponseEntity.ok(toResponseList(service.findAllByPeriodo(anho, mes, limit)));
    }

    @GetMapping("/unique/{legajoId}/{anho}/{mes}")
    public ResponseEntity<LetraResponse> findByUnique(@PathVariable Long legajoId, @PathVariable Integer anho,
            @PathVariable Integer mes) {
        try {
            return ResponseEntity.ok(letraDtoMapper.toResponse(service.findByUnique(legajoId, anho, mes)));
        } catch (LetraException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<LetraResponse> add(@Valid @RequestBody LetraRequest request) {
        Letra created = service.add(letraDtoMapper.toDomain(request));
        return new ResponseEntity<>(letraDtoMapper.toResponse(created), HttpStatus.CREATED);
    }

    @PutMapping("/{letraId}")
    public ResponseEntity<LetraResponse> update(@Valid @RequestBody LetraRequest request, @PathVariable Long letraId) {
        try {
            Letra updated = service.update(letraDtoMapper.toDomain(request), letraId);
            return ResponseEntity.ok(letraDtoMapper.toResponse(updated));
        } catch (LetraException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/")
    public ResponseEntity<List<LetraResponse>> saveAll(@Valid @RequestBody List<LetraRequest> requests) {
        List<Letra> letras = requests.stream()
                .map(letraDtoMapper::toDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(toResponseList(service.saveAllLetras(letras)));
    }

    @DeleteMapping("/periodo/{anho}/{mes}")
    public ResponseEntity<Void> deleteByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
        service.deleteByPeriodo(anho, mes);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private List<LetraResponse> toResponseList(List<Letra> letras) {
        return letras.stream()
                .map(letraDtoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
