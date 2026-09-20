package um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;
import um.haberes.core.hexagonal.cursos.designacion_tipo.application.exception.DesignacionTipoException;
import um.haberes.core.hexagonal.cursos.designacion_tipo.application.service.DesignacionTipoService;
import um.haberes.core.hexagonal.cursos.designacion_tipo.domain.model.DesignacionTipo;
import um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.web.dto.DesignacionTipoResponse;
import um.haberes.core.hexagonal.cursos.designacion_tipo.infrastructure.web.mapper.DesignacionTipoDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/designaciontipo")
@RequiredArgsConstructor
public class DesignacionTipoController {

    private final DesignacionTipoService service;
    private final DesignacionTipoDtoMapper designacionTipoDtoMapper;

    @GetMapping("/")
    public ResponseEntity<List<DesignacionTipoResponse>> findAll() {
        return ResponseEntity.ok(toResponseList(service.findAll()));
    }

    @GetMapping("/{designacionTipoId}")
    public ResponseEntity<DesignacionTipoResponse> findByDesignacionTipoId(@PathVariable Integer designacionTipoId) {
        try {
            return ResponseEntity.ok(designacionTipoDtoMapper.toResponse(service.findByDesignacionTipoId(designacionTipoId)));
        } catch (DesignacionTipoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private List<DesignacionTipoResponse> toResponseList(List<DesignacionTipo> designacionTipos) {
        return designacionTipos.stream()
                .map(designacionTipoDtoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
