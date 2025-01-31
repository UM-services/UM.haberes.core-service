package um.haberes.core.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.haberes.core.kotlin.model.view.TotalNovedad;
import um.haberes.core.service.view.TotalNovedadService;

import java.util.List;

@RestController
@RequestMapping("/api/haberes/core/totalnovedad")
public class TotalNovedadController {

    @Autowired
    private TotalNovedadService service;

    @GetMapping("/periodo/{anho}/{mes}")
    public ResponseEntity<List<TotalNovedad>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
        return new ResponseEntity<>(service.findAllByPeriodo(anho, mes), HttpStatus.OK);
    }

    @GetMapping("/unique/{anho}/{mes}/{codigoId}")
    public ResponseEntity<TotalNovedad> findByUnique(@PathVariable Integer anho, @PathVariable Integer mes,
                                                     @PathVariable Integer codigoId) {
        return new ResponseEntity<>(service.findByUnique(anho, mes, codigoId), HttpStatus.OK);
    }
}
