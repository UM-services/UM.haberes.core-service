package um.haberes.rest.controller.extern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.haberes.rest.kotlin.model.extern.ContratadoPersonaDto;
import um.haberes.rest.service.extern.ContratadoPersonaService;

import java.util.List;

@RestController
@RequestMapping("/api/haberes/core/contratadoPersona")
public class ContratadoPersonaController {

    @Autowired
    private ContratadoPersonaService service;

    @GetMapping("/")
    public ResponseEntity<List<ContratadoPersonaDto>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }
}
