package um.haberes.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.haberes.core.kotlin.model.ModoLiquidacion;
import um.haberes.core.service.ModoLiquidacionService;

import java.util.List;

@RestController
@RequestMapping("/api/haberes/core/modoLiquidacion")
public class ModoLiquidacionController {

    private final ModoLiquidacionService service;

    @Autowired
    public ModoLiquidacionController(ModoLiquidacionService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<ModoLiquidacion>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{modoLiquidacionId}")
    public ResponseEntity<ModoLiquidacion> findByModoLiquidacionId(@PathVariable Integer modoLiquidacionId) {
        return new ResponseEntity<>(service.findByModoLiquidacionId(modoLiquidacionId), HttpStatus.OK);
    }

}
