package um.haberes.core.controller.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.haberes.core.kotlin.model.internal.OrdenPagoRequest;
import um.haberes.core.service.facade.OrdenPagoService;

@RestController
@RequestMapping("/api/haberes/core/ordenPago")
public class OrdenPagoController {

    private final OrdenPagoService service;

    @Autowired
    public OrdenPagoController(OrdenPagoService service) {
        this.service = service;
    }

    @PostMapping("/generate")
    public ResponseEntity<Boolean> generateOrdenPago(@RequestBody OrdenPagoRequest ordenPagoRequest) {
        return new ResponseEntity<>(service.generateOrdenPago(ordenPagoRequest), HttpStatus.OK);
    }

}
