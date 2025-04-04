package um.haberes.core.controller.extern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.haberes.core.kotlin.model.extern.CursoCargoContratadoDto;
import um.haberes.core.service.extern.CursoCargoContratadoService;

import java.util.List;

@RestController
@RequestMapping("/api/haberes/core/cursoCargoContratado")
public class CursoCargoContratadoController {

    private final CursoCargoContratadoService service;

    @Autowired
    public CursoCargoContratadoController(CursoCargoContratadoService service) {
        this.service = service;
    }

    @GetMapping("/curso/{cursoId}/{anho}/{mes}")
    public ResponseEntity<List<CursoCargoContratadoDto>> findAllByCurso(@PathVariable Long cursoId, @PathVariable Integer anho, @PathVariable Integer mes) {
        return new ResponseEntity<>(service.findAllByCurso(cursoId, anho, mes), HttpStatus.OK);
    }
}
