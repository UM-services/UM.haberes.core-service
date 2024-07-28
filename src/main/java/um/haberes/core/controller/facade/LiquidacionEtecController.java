package um.haberes.core.controller.facade;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.haberes.core.service.facade.LiquidacionEtecService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/haberes/core/liquidacion-etec")
public class LiquidacionEtecController {

    private final LiquidacionEtecService service;

    public LiquidacionEtecController(LiquidacionEtecService service) {
        this.service = service;
    }

    @GetMapping("/calcularPorcentajeAntiguedad/{legajoId}/{anho}/{mes}/{facultadId}/{geograficaId}")
    public ResponseEntity<BigDecimal> calcularPorcentajeAntiguedad(@PathVariable Long legajoId,
                                                                   @PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Integer facultadId, @PathVariable Integer geograficaId) {
        BigDecimal porcentajeAntiguedad = service.calcularPorcentajeAntiguedad(legajoId, anho, mes, facultadId, geograficaId);
        return new ResponseEntity<>(porcentajeAntiguedad, HttpStatus.OK);
    }

}
