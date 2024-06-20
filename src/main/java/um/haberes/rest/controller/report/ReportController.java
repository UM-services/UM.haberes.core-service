package um.haberes.rest.controller.report;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.haberes.rest.service.report.AnotadorAutorizadosRechazadosService;
import um.haberes.rest.service.report.AnotadorPendientesService;
import um.haberes.rest.service.report.DocenteSedeService;
import um.haberes.rest.service.report.NovedadesDocentesService;
import um.haberes.rest.util.Tool;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final DocenteSedeService docenteSedeService;

    private final AnotadorPendientesService anotadorPendientesService;

    private final AnotadorAutorizadosRechazadosService anotadorAutorizadosRechazadosService;

    private final NovedadesDocentesService novedadesDocentesService;

    public ReportController(DocenteSedeService docenteSedeService, AnotadorPendientesService anotadorPendientesService, AnotadorAutorizadosRechazadosService anotadorAutorizadosRechazadosService, NovedadesDocentesService novedadesDocentesService) {
        this.docenteSedeService = docenteSedeService;
        this.anotadorPendientesService = anotadorPendientesService;
        this.anotadorAutorizadosRechazadosService = anotadorAutorizadosRechazadosService;
        this.novedadesDocentesService = novedadesDocentesService;
    }

    @GetMapping("/docenteSede/{facultadId}/{geograficaId}/{anho}/{mes}")
    public ResponseEntity<Resource> generateDocenteSede(@PathVariable Integer facultadId, @PathVariable Integer geograficaId, @PathVariable Integer anho, @PathVariable Integer mes) throws FileNotFoundException {
        return Tool.generateFile(docenteSedeService.generate(facultadId, geograficaId, anho, mes), "docentesSede.pdf");
    }

    @GetMapping("/anotadorPendientes/{facultadId}/{anho}/{mes}")
    public ResponseEntity<Resource> generateAnotadorPendientes(@PathVariable Integer facultadId, @PathVariable Integer anho, @PathVariable Integer mes) throws FileNotFoundException {
        return Tool.generateFile(anotadorPendientesService.generate(facultadId, anho, mes), "anotadorPendientes.pdf");
    }

    @GetMapping("/anotadorAutorizadosRechazados/{facultadId}/{anho}/{mes}")
    public ResponseEntity<Resource> generateAnotadorAutorizadosRechazados(@PathVariable Integer facultadId, @PathVariable Integer anho, @PathVariable Integer mes) throws FileNotFoundException {
        return Tool.generateFile(anotadorAutorizadosRechazadosService.generate(facultadId, anho, mes), "anotadorAutorizadosRechazados.pdf");
    }

    @GetMapping("/novedadesDocentes/{facultadId}/{anho}/{mes}")
    public ResponseEntity<Resource> generateNovedadesDocentes(@PathVariable Integer facultadId, @PathVariable Integer anho, @PathVariable Integer mes) throws FileNotFoundException {
        return Tool.generateFile(novedadesDocentesService.generate(facultadId, anho, mes), "novedadesDocentes.pdf");
    }

}
