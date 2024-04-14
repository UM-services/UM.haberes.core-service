package um.haberes.rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import um.haberes.rest.kotlin.model.extern.CuentaMovimientoDto;

import java.time.OffsetDateTime;
import java.util.List;

@FeignClient(name = "core-service/cuentaMovimiento")
public interface CuentaMovimientoClient {

    @GetMapping("/asiento/{fechaContable}/{ordenContable}")
    List<CuentaMovimientoDto> findAllByAsiento(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fechaContable, @PathVariable Integer ordenContable);

    @GetMapping("/entregaDetalle/{proveedorMovimientoId}")
    List<CuentaMovimientoDto> findAllEntregaDetalleByProveedorMovimientoId(@PathVariable Long proveedorMovimientoId);

    @PostMapping("/entregaDetalle")
    List<CuentaMovimientoDto> findAllEntregaDetalleByProveedorMovimientoIds(@RequestBody List<Long> proveedorMovimientoIds);

    @GetMapping("/{cuentaMovimientoId}")
    CuentaMovimientoDto findByCuentaMovimientoId(@PathVariable Long cuentaMovimientoId);

}
