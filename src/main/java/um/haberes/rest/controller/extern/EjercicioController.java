package um.haberes.rest.controller.extern;

import um.haberes.rest.exception.extern.EjercicioException;
import um.haberes.rest.kotlin.model.extern.Ejercicio;
import um.haberes.rest.service.extern.EjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/ejercicio")
public class EjercicioController {

    @Autowired
    private EjercicioService service;

    @GetMapping("/periodo/{anho}/{mes}")
    public ResponseEntity<Ejercicio> findByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
        try {
            return new ResponseEntity<>(service.findByPeriodo(anho, mes), HttpStatus.OK);
        } catch (EjercicioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
