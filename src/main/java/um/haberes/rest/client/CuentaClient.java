package um.haberes.rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import um.haberes.rest.kotlin.model.extern.CuentaDto;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "core-service/cuenta")
public interface CuentaClient {

    @GetMapping("/")
    List<CuentaDto> findAll();

    @GetMapping("/cierreresultado")
    List<CuentaDto> findAllByCierreResultado();

    @GetMapping("/cierreactivopasivo")
    List<CuentaDto> findAllByCierreActivoPasivo();

    @PostMapping("/search/{visible}")
    List<CuentaDto> findByStrings(@RequestBody List<String> conditions, @PathVariable Boolean visible);

    @GetMapping("/{numeroCuenta}")
    CuentaDto findByNumeroCuenta(@PathVariable BigDecimal numeroCuenta);

    @GetMapping("/id/{cuentaContableId}")
    CuentaDto findByCuentaContableId(@PathVariable Long cuentaContableId);

    @PostMapping("/")
    CuentaDto add(@RequestBody CuentaDto cuenta);

    @PutMapping("/{numeroCuenta}")
    CuentaDto update(@RequestBody CuentaDto cuenta, @PathVariable BigDecimal numeroCuenta);

    @DeleteMapping("/{numeroCuenta}")
    Void delete(@PathVariable BigDecimal numeroCuenta);

    @GetMapping("/recalculagrados")
    String recalculaGrados();

}
