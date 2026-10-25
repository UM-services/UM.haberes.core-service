package um.haberes.core.controller.view;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.model.view.LegajoCursoCantidad;
import um.haberes.core.service.view.LegajoCursoCantidadService;

/**
 * Vista {@code vw_legajo_curso_cantidad} por período. Endpoint de sólo lectura que consume
 * el servicio de reportes para la planilla de cantidad de cargos; no reemplaza ninguna ruta
 * usada por el cliente de escritorio.
 */
@RestController
@RequestMapping("/api/haberes/core/legajocursoCantidad")
@RequiredArgsConstructor
public class LegajoCursoCantidadController {

    private final LegajoCursoCantidadService service;

    @GetMapping("/periodo/{anho}/{mes}")
    public ResponseEntity<List<LegajoCursoCantidad>> findAllByPeriodo(@PathVariable Integer anho,
                                                                      @PathVariable Integer mes) {
        return ResponseEntity.ok(service.findAllByPeriodo(anho, mes));
    }
}
