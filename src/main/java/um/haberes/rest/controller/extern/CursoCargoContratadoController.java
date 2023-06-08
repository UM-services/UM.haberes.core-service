package um.haberes.rest.controller.extern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.haberes.rest.kotlin.model.extern.CursoCargoContratado;
import um.haberes.rest.service.extern.CursoCargoContratadoService;

import java.util.List;

@RestController
@RequestMapping("/cursoCargoContratado")
public class CursoCargoContratadoController {

    @Autowired
    private CursoCargoContratadoService service;

    @GetMapping("/curso/{cursoId}/{anho}/{mes}")
    public ResponseEntity<List<CursoCargoContratado>> findAllByCurso(@PathVariable Long cursoId, @PathVariable Integer anho, @PathVariable Integer mes) {
        return new ResponseEntity<>(service.findAllByCurso(cursoId, anho, mes), HttpStatus.OK);
    }
}
