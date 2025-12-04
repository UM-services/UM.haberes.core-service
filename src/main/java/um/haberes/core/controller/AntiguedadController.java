/**
 *
 */
package um.haberes.core.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import um.haberes.core.exception.AntiguedadException;
import um.haberes.core.exception.view.AntiguedadPeriodoException;
import um.haberes.core.kotlin.model.Antiguedad;
import um.haberes.core.kotlin.model.view.AntiguedadPeriodo;
import um.haberes.core.service.AntiguedadService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/antiguedad")
public class AntiguedadController {

    private final AntiguedadService service;

    public AntiguedadController(AntiguedadService service) {
        this.service = service;
    }

    @GetMapping("/unique/{legajoId}/{anho}/{mes}")
    public ResponseEntity<Antiguedad> findByUnique(@PathVariable Long legajoId, @PathVariable Integer anho,
                                                   @PathVariable Integer mes) {
        try {
            return new ResponseEntity<>(service.findByUnique(legajoId, anho, mes), HttpStatus.OK);
        } catch (AntiguedadException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/last/{legajoId}/{anho}/{mes}")
    public ResponseEntity<AntiguedadPeriodo> findLastByUnique(@PathVariable Long legajoId, @PathVariable Integer anho,
                                                              @PathVariable Integer mes) {
        try {
            return new ResponseEntity<AntiguedadPeriodo>(service.findLastByUnique(legajoId, anho, mes), HttpStatus.OK);
        } catch (AntiguedadPeriodoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/periodo/{anho}/{mes}/{limit}")
    public ResponseEntity<List<Antiguedad>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes,
                                                             @PathVariable Integer limit) {
        if (limit == 0)
            limit = 30000;
        return new ResponseEntity<>(service.findAllByPeriodo(anho, mes, limit), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Antiguedad> add(@RequestBody Antiguedad antiguedad) {
        return new ResponseEntity<>(service.add(antiguedad), HttpStatus.OK);
    }

    @PutMapping("/{antiguedadId}")
    public ResponseEntity<Antiguedad> update(@RequestBody Antiguedad antiguedad, @PathVariable Long antiguedadId) {
        return new ResponseEntity<>(service.update(antiguedad, antiguedadId), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<List<Antiguedad>> saveAll(@RequestBody List<Antiguedad> antiguedades) {
        return new ResponseEntity<>(service.saveAll(antiguedades), HttpStatus.OK);
    }

    @GetMapping("/calculate/{legajoId}/{anho}/{mes}")
    public ResponseEntity<Void> calculate(@PathVariable Long legajoId, @PathVariable Integer anho,
                                          @PathVariable Integer mes) {
        service.calculate(legajoId, anho, mes);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
