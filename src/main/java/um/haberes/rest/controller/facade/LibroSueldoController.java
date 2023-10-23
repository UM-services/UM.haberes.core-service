package um.haberes.rest.controller.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.haberes.rest.service.facade.LibroSueldoService;
import um.haberes.rest.util.Tool;

import java.io.IOException;

@RestController
@RequestMapping("/libroSueldo")
public class LibroSueldoController {

    private final LibroSueldoService service;

    @Autowired
    public LibroSueldoController(LibroSueldoService service) {
        this.service = service;
    }

    @GetMapping("/generate/{anho}/{mes}")
    public ResponseEntity<Resource> generate(
            @PathVariable Integer anho, @PathVariable Integer mes) throws IOException {
        return Tool.generateFile(service.generate(anho, mes), "libro_sueldos.zip");
    }

}
