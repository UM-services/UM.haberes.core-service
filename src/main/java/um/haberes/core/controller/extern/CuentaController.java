package um.haberes.core.controller.extern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import um.haberes.core.exception.extern.CuentaException;
import um.haberes.core.kotlin.model.extern.CuentaDto;
import um.haberes.core.service.extern.CuentaService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/haberes/core/cuenta")
public class CuentaController {

    @Autowired
    private CuentaService service;

    @GetMapping("/")
    public ResponseEntity<List<CuentaDto>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping("/search/{visible}")
    public ResponseEntity<List<CuentaDto>> findByStrings(@RequestBody List<String> conditions, @PathVariable Boolean visible) {
        return new ResponseEntity<>(service.findByStrings(conditions, visible), HttpStatus.OK);
    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<CuentaDto> findByNumeroCuenta(@PathVariable BigDecimal numeroCuenta) {
        try {
            return new ResponseEntity<>(service.findByNumeroCuenta(numeroCuenta), HttpStatus.OK);
        } catch (CuentaException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/id/{cuentaContableId}")
    public ResponseEntity<CuentaDto> findByCuentaContableId(@PathVariable Long cuentaContableId) {
        try {
            return new ResponseEntity<>(service.findByCuentaContableId(cuentaContableId), HttpStatus.OK);
        } catch (CuentaException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
