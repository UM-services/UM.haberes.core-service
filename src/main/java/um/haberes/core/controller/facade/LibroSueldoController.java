package um.haberes.core.controller.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.haberes.core.service.facade.LibroSueldoService;
import um.haberes.core.util.Tool;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/haberes/core/libroSueldo")
public class LibroSueldoController {

    private final LibroSueldoService service;

    @Autowired
    public LibroSueldoController(LibroSueldoService service) {
        this.service = service;
    }

    @PostMapping("/generate/{anho}/{mes}")
    public ResponseEntity<Resource> generate(
            @PathVariable Integer anho, @PathVariable Integer mes, @RequestBody List<Long> legajoIds) throws IOException {
        return Tool.generateFile(service.generate(anho, mes, legajoIds), "libro_sueldos.zip");
    }

}
