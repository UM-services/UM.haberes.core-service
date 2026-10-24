package um.haberes.core.controller.facade;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.service.facade.FusionPeriodoService;

/**
 * Procesos masivos por período del circuito de designaciones. El cliente envía
 * el lote de legajos (la UI lo divide para acotar la duración de cada request) y
 * recibe el resumen con los fallos por legajo.
 */
@RestController
@RequestMapping("/api/haberes/core/designaciontool/periodo")
public class FusionPeriodoController {

    private final FusionPeriodoService service;

    public FusionPeriodoController(FusionPeriodoService service) {
        this.service = service;
    }

    @PostMapping("/fusionar/{anho}/{mes}")
    public ResponseEntity<FusionPeriodoService.ResumenLote> fusionar(@PathVariable Integer anho,
            @PathVariable Integer mes, @RequestBody LoteRequest request) {
        return new ResponseEntity<>(service.procesarFusion(legajos(request), anho, mes), HttpStatus.OK);
    }

    @PostMapping("/desarraigo/{anho}/{mes}")
    public ResponseEntity<FusionPeriodoService.ResumenLote> desarraigo(@PathVariable Integer anho,
            @PathVariable Integer mes, @RequestBody LoteRequest request) {
        return new ResponseEntity<>(service.procesarDesarraigo(legajos(request), anho, mes), HttpStatus.OK);
    }

    @PostMapping("/duplicar/{anho}/{mes}")
    public ResponseEntity<FusionPeriodoService.ResumenLote> duplicar(@PathVariable Integer anho,
            @PathVariable Integer mes, @RequestBody LoteRequest request) {
        return new ResponseEntity<>(service.procesarDuplicado(legajos(request), anho, mes), HttpStatus.OK);
    }

    @PostMapping("/depurar/{anho}/{mes}")
    public ResponseEntity<Void> depurar(@PathVariable Integer anho, @PathVariable Integer mes) {
        service.depurarZombies(anho, mes);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private List<Long> legajos(LoteRequest request) {
        return request == null || request.legajos() == null ? List.of() : request.legajos();
    }

    public record LoteRequest(List<Long> legajos) {
    }

}
