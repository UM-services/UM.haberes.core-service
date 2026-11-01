package um.haberes.core.controller.view;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.model.view.NovedadAcumulado;
import um.haberes.core.service.view.NovedadAcumuladoService;

/**
 * Vista {@code vw_novedad_acumulado} por código y período. Endpoint de sólo lectura que
 * consume el servicio de reportes para el detalle por código.
 */
@RestController
@RequestMapping("/api/haberes/core/novedadacumulado")
@RequiredArgsConstructor
public class NovedadAcumuladoController {

    private final NovedadAcumuladoService service;

    @GetMapping("/codigo/{codigoId}/{anho}/{mes}")
    public ResponseEntity<List<NovedadAcumulado>> findAllByCodigo(@PathVariable Integer codigoId,
                                                                  @PathVariable Integer anho,
                                                                  @PathVariable Integer mes) {
        return ResponseEntity.ok(service.findAllByCodigo(codigoId, anho, mes));
    }
}
