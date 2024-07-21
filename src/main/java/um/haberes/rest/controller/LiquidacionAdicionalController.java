package um.haberes.rest.controller;

import java.util.List;

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

import um.haberes.rest.exception.LiquidacionAdicionalException;
import um.haberes.rest.kotlin.model.LiquidacionAdicional;
import um.haberes.rest.service.LiquidacionAdicionalService;

@RestController
@RequestMapping("/api/haberes/core/liquidacion-adicional")
public class LiquidacionAdicionalController {

    private final LiquidacionAdicionalService service;

    public LiquidacionAdicionalController(LiquidacionAdicionalService service) {
        this.service = service;
    }

    @GetMapping("/legajo/{legajoId}/{anho}/{mes}")
    public ResponseEntity<List<LiquidacionAdicional>> findAllByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho, @PathVariable Integer mes) {
        return new ResponseEntity<>(service.findAllByLegajo(legajoId, anho, mes), HttpStatus.OK);
    }

    @GetMapping("/dependencia/{legajoId}/{anho}/{mes}/{dependenciaId}")
    public ResponseEntity<LiquidacionAdicional> findByDependencia(@PathVariable Long legajoId, @PathVariable Integer anho, @PathVariable Integer mes, @PathVariable Integer dependenciaId) {
        try {
            return new ResponseEntity<>(service.findByDependencia(legajoId, anho, mes, dependenciaId), HttpStatus.OK);
        } catch (LiquidacionAdicionalException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/legajo/{legajoId}/{anho}/{mes}")
    public ResponseEntity<Void> deleteAllByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho, @PathVariable Integer mes) {
        service.deleteAllByLegajo(legajoId, anho, mes);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/")
    public ResponseEntity<LiquidacionAdicional> add(@RequestBody LiquidacionAdicional liquidacionAdicional) {
        return new ResponseEntity<>(service.add(liquidacionAdicional), HttpStatus.OK);
    }

}
