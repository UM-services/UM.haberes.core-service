package um.haberes.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import um.haberes.rest.exception.AfipSituacionException;
import um.haberes.rest.kotlin.model.AfipSituacion;
import um.haberes.rest.service.AfipSituacionService;

import java.util.List;

@RestController
@RequestMapping("/api/haberes/core/afipSituacion")
public class AfipSituacionController {

    private final AfipSituacionService service;

    public AfipSituacionController(AfipSituacionService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<AfipSituacion>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{afipSituacionId}")
    public ResponseEntity<AfipSituacion> findByAfipSituacionId(@PathVariable Integer afipSituacionId) {
        try {
            return new ResponseEntity<>(service.findByAfipSituacionId(afipSituacionId), HttpStatus.OK);
        } catch (AfipSituacionException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
