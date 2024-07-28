package um.haberes.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.haberes.core.kotlin.model.AfipConceptoSueldo;
import um.haberes.core.kotlin.model.view.AfipConceptoSueldoSearch;
import um.haberes.core.service.AfipConceptoSueldoService;

import java.util.List;

@RestController
@RequestMapping("/api/haberes/core/afipConceptoSueldo")
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
