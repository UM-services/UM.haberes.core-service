package um.haberes.rest.controller.extern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import um.haberes.rest.exception.extern.CuentaException;
import um.haberes.rest.kotlin.model.extern.Cuenta;
import um.haberes.rest.service.extern.CuentaService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cuenta")
public class CuentaController {

    @Autowired
    private CuentaService service;

    @GetMapping("/")
    public ResponseEntity<List<Cuenta>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<Cuenta> findByNumeroCuenta(@PathVariable BigDecimal numeroCuenta) {
        try {
            return new ResponseEntity<>(service.findByNumeroCuenta(numeroCuenta), HttpStatus.OK);
        } catch (CuentaException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/id/{cuentaContableId}")
    public ResponseEntity<Cuenta> findByCuentaContableId(@PathVariable Long cuentaContableId) {
        try {
            return new ResponseEntity<>(service.findByCuentaContableId(cuentaContableId), HttpStatus.OK);
        } catch (CuentaException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
