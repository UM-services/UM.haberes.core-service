package um.haberes.core.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.haberes.core.kotlin.model.CursoCargo;
import um.haberes.core.kotlin.model.view.DocenteDesignacion;
import um.haberes.core.service.view.DocenteDesignacionService;

import java.util.List;

@RestController
@RequestMapping("/api/haberes/core/docenteDesignacion")
public class DocenteDesignacionController {

    private final DocenteDesignacionService service;

    public DocenteDesignacionController(DocenteDesignacionService service) {
        this.service = service;
    }

    @GetMapping("/periodo/{anho}/{mes}")
    public ResponseEntity<List<DocenteDesignacion>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
        return new ResponseEntity<>(service.findAllByPeriodo(anho, mes), HttpStatus.OK);
    }

    @GetMapping("/legajo/{legajoId}/{anho}/{mes}")
    public ResponseEntity<List<DocenteDesignacion>> findAllByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
                                                            @PathVariable Integer mes) {
        return new ResponseEntity<>(service.findAllByLegajo(legajoId, anho, mes), HttpStatus.OK);
    }
}
