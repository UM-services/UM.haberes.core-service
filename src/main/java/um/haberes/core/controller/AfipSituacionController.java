package um.haberes.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import um.haberes.core.exception.AfipSituacionException;
import um.haberes.core.model.AfipSituacionEntity;
import um.haberes.core.service.AfipSituacionService;

import java.util.List;

@RestController
@RequestMapping("/api/haberes/core/afipSituacion")
public class AfipSituacionController {

    private final AfipSituacionService service;

    public AfipSituacionController(AfipSituacionService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<AfipSituacionEntity>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{afipSituacionId}")
    public ResponseEntity<AfipSituacionEntity> findByAfipSituacionId(@PathVariable Integer afipSituacionId) {
        try {
            return new ResponseEntity<>(service.findByAfipSituacionId(afipSituacionId), HttpStatus.OK);
        } catch (AfipSituacionException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
