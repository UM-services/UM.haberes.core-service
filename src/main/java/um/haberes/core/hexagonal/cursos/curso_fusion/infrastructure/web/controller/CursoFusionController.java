package um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.web.controller;

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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.cursos.curso_fusion.application.service.CursoFusionService;
import um.haberes.core.hexagonal.cursos.curso_fusion.domain.model.CursoFusion;
import um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.web.dto.CursoFusionRequest;
import um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.web.dto.CursoFusionResponse;
import um.haberes.core.hexagonal.cursos.curso_fusion.infrastructure.web.mapper.CursoFusionDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/cursofusion")
@RequiredArgsConstructor
public class CursoFusionController {

    private final CursoFusionService service;
    private final CursoFusionDtoMapper cursoFusionDtoMapper;

    @GetMapping("/legajo/{legajoId}/{anho}/{mes}")
    public ResponseEntity<List<CursoFusionResponse>> findAllByLegajoId(@PathVariable Long legajoId,
            @PathVariable Integer anho, @PathVariable Integer mes) {
        return ResponseEntity.ok(toResponseList(service.findAllByLegajoId(legajoId, anho, mes)));
    }

    @GetMapping("/legajofacultad/{legajoId}/{anho}/{mes}/{facultadId}")
    public ResponseEntity<List<CursoFusionResponse>> findAllByLegajoIdAndFacultadId(@PathVariable Long legajoId,
            @PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Integer facultadId) {
        return ResponseEntity.ok(toResponseList(
                service.findAllByLegajoIdAndFacultadId(legajoId, anho, mes, facultadId)));
    }

    @PostMapping("/")
    public ResponseEntity<CursoFusionResponse> add(@Valid @RequestBody CursoFusionRequest request) {
        return new ResponseEntity<>(
                cursoFusionDtoMapper.toResponse(service.add(cursoFusionDtoMapper.toDomain(request))), HttpStatus.OK);
    }

    @DeleteMapping("/{cursoFusionId}")
    public ResponseEntity<Void> deleteByCursofusionId(@PathVariable Long cursoFusionId) {
        service.deleteByCursoFusionId(cursoFusionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/facultad/{legajoId}/{anho}/{mes}/{facultadId}/{geograficaId}")
    public ResponseEntity<Void> deleteByFacultadId(@PathVariable Long legajoId, @PathVariable Integer anho,
            @PathVariable Integer mes, @PathVariable Integer facultadId, @PathVariable Integer geograficaId) {
        service.deleteAllByLegajoIdAndAnhoAndMesAndFacultadIdAndGeograficaId(legajoId, anho, mes, facultadId,
                geograficaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private List<CursoFusionResponse> toResponseList(List<CursoFusion> cursoFusiones) {
        return cursoFusiones.stream()
                .map(cursoFusionDtoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
