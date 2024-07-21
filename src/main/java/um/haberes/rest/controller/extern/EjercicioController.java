package um.haberes.rest.controller.extern;

import org.springframework.format.annotation.DateTimeFormat;
import um.haberes.rest.exception.extern.EjercicioException;
import um.haberes.rest.kotlin.model.extern.EjercicioDto;
import um.haberes.rest.service.extern.EjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/haberes/core/ejercicio")
public class EjercicioController {

    @Autowired
    private EjercicioService service;

    @GetMapping("/periodo/{anho}/{mes}")
    public ResponseEntity<EjercicioDto> findByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
        try {
            return new ResponseEntity<>(service.findByPeriodo(anho, mes), HttpStatus.OK);
        } catch (EjercicioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<EjercicioDto> findByFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fecha) {
        try {
            return new ResponseEntity<>(service.findByFecha(fecha), HttpStatus.OK);
        } catch (EjercicioException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
