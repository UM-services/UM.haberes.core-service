package um.haberes.rest.controller.report;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.haberes.rest.service.report.AnotadorPendientesService;
import um.haberes.rest.service.report.DocenteSedeService;
import um.haberes.rest.util.Tool;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final DocenteSedeService docenteSedeService;

    private final AnotadorPendientesService anotadorPendientesService;

    public ReportController(DocenteSedeService docenteSedeService, AnotadorPendientesService anotadorPendientesService) {
        this.docenteSedeService = docenteSedeService;
        this.anotadorPendientesService = anotadorPendientesService;
    }

    @GetMapping("/docenteSede/{facultadId}/{geograficaId}/{anho}/{mes}")
    public ResponseEntity<Resource> generateDocenteSede(@PathVariable Integer facultadId, @PathVariable Integer geograficaId, @PathVariable Integer anho, @PathVariable Integer mes) throws FileNotFoundException {
        return Tool.generateFile(docenteSedeService.generate(facultadId, geograficaId, anho, mes), "docentesSede.pdf");
    }

    @GetMapping("/anotadorPendientes/{facultadId}/{anho}/{mes}")
    public ResponseEntity<Resource> generateAnotadorPendientes(@PathVariable Integer facultadId, @PathVariable Integer anho, @PathVariable Integer mes) throws FileNotFoundException {
        return Tool.generateFile(anotadorPendientesService.generate(facultadId, anho, mes), "anotadorPendientes.pdf");
    }

}
