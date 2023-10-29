package um.haberes.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.haberes.rest.kotlin.model.AfipConceptoSueldo;
import um.haberes.rest.kotlin.model.view.AfipConceptoSueldoSearch;
import um.haberes.rest.service.AfipConceptoSueldoService;

import java.util.List;

@RestController
@RequestMapping("/afipConceptoSueldo")
public class AfipConceptoSueldoController {

    @Autowired
    private AfipConceptoSueldoService service;

    @GetMapping("/{afipConceptoSueldoId}")
    public ResponseEntity<AfipConceptoSueldo> findByAfipConceptoSueldoId(@PathVariable Long afipConceptoSueldoId) {
        return new ResponseEntity<>(service.findByAfipConceptoSueldoId(afipConceptoSueldoId), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<AfipConceptoSueldoSearch>> findAllByAsignadoAndConditions(@RequestBody List<String> conditions) {
        return new ResponseEntity<>(service.findAllByAsignadoAndConditions(conditions), HttpStatus.OK);
    }

}
