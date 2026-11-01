package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.controller;

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
import lombok.extern.slf4j.Slf4j;
import um.haberes.core.hexagonal.liquidaciones.categoria.application.exception.CategoriaException;
import um.haberes.core.hexagonal.liquidaciones.categoria.application.service.CategoriaService;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.dto.CategoriaRequest;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.dto.CategoriaResponse;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.dto.CategoriaSearchResponse;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.dto.CategoriaUploadRequest;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.mapper.CategoriaDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/categoria")
@RequiredArgsConstructor
@Slf4j
public class CategoriaController {

    private final CategoriaService service;
    private final CategoriaDtoMapper categoriaDtoMapper;

    @GetMapping("/")
    public ResponseEntity<List<CategoriaResponse>> findAll() {
        return ResponseEntity.ok(toResponseList(service.findAll()));
    }

    @GetMapping("/nogrado")
    public ResponseEntity<List<CategoriaResponse>> findAllNoGrado() {
        return ResponseEntity.ok(toResponseList(service.findAllNoDocentes()));
    }

    @GetMapping("/search/{chain}")
    public ResponseEntity<List<CategoriaSearchResponse>> findAllSearch(@PathVariable String chain) {
        return ResponseEntity.ok(service.findAllSearch(chain).stream()
                .map(categoriaDtoMapper::toSearchResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/nodocente/{anho}/{mes}")
    public ResponseEntity<List<CategoriaResponse>> findAllNoDocente(@PathVariable Integer anho,
            @PathVariable Integer mes) {
        return ResponseEntity.ok(toResponseList(service.findAllNoDocenteByPeriodo(anho, mes)));
    }

    @GetMapping("/nodocentelegajo/{legajoId}/{anho}/{mes}")
    public ResponseEntity<List<CategoriaResponse>> findAllNoDocentesByLegajoId(@PathVariable Long legajoId,
            @PathVariable Integer anho, @PathVariable Integer mes) {
        return ResponseEntity.ok(toResponseList(service.findAllNoDocenteByLegajoId(legajoId, anho, mes)));
    }

    @GetMapping("/{categoriaId}")
    public ResponseEntity<CategoriaResponse> findByCategoriaId(@PathVariable Integer categoriaId) {
        try {
            return ResponseEntity.ok(categoriaDtoMapper.toResponse(service.findByCategoriaId(categoriaId)));
        } catch (CategoriaException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/last")
    public ResponseEntity<CategoriaResponse> findLast() {
        try {
            return ResponseEntity.ok(categoriaDtoMapper.toResponse(service.findLast()));
        } catch (CategoriaException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{categoriaId}")
    public ResponseEntity<Void> delete(@PathVariable Integer categoriaId) {
        service.delete(categoriaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{anho}/{mes}")
    public ResponseEntity<CategoriaResponse> add(@Valid @RequestBody CategoriaRequest request,
            @PathVariable Integer anho, @PathVariable Integer mes) {
        Categoria created = service.add(categoriaDtoMapper.toDomain(request), anho, mes);
        return new ResponseEntity<>(categoriaDtoMapper.toResponse(created), HttpStatus.OK);
    }

    @PutMapping("/{categoriaId}/{anho}/{mes}")
    public ResponseEntity<CategoriaResponse> update(@Valid @RequestBody CategoriaRequest request,
            @PathVariable Integer categoriaId, @PathVariable Integer anho, @PathVariable Integer mes) {
        log.debug("CategoriaRequest -> {}", request);
        Categoria updated = service.update(categoriaDtoMapper.toDomain(request), categoriaId, anho, mes);
        return ResponseEntity.ok(categoriaDtoMapper.toResponse(updated));
    }

    @PutMapping("/all/{anho}/{mes}")
    public ResponseEntity<List<CategoriaResponse>> saveAll(@Valid @RequestBody List<CategoriaRequest> requests,
            @PathVariable Integer anho, @PathVariable Integer mes) {
        List<Categoria> categorias = requests.stream()
                .map(categoriaDtoMapper::toDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(toResponseList(service.saveAll(categorias, anho, mes)));
    }

    @PostMapping("/upload/{anho}/{mes}")
    public ResponseEntity<Void> upload(@Valid @RequestBody CategoriaUploadRequest request,
            @PathVariable Integer anho, @PathVariable Integer mes) {
        try {
            service.upload(categoriaDtoMapper.toUploadedFile(request), anho, mes);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CategoriaException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private List<CategoriaResponse> toResponseList(List<Categoria> categorias) {
        return categorias.stream()
                .map(categoriaDtoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
