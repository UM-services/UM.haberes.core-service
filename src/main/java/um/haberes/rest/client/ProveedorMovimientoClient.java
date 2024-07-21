package um.haberes.rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import um.haberes.rest.kotlin.model.extern.ProveedorMovimientoDto;

import java.time.OffsetDateTime;
import java.util.List;

@FeignClient(name = "tesoreria-core-service/proveedorMovimiento")
public interface ProveedorMovimientoClient {

    @GetMapping("/eliminables/{ejercicioId}")
    List<ProveedorMovimientoDto> findAllEliminables(@PathVariable Integer ejercicioId);

    @GetMapping("/asignables/{proveedorId}/{desde}/{hasta}/{geograficaId}/{todos}")
    List<ProveedorMovimientoDto> findAllAsignables(@PathVariable Integer proveedorId,
                                                   @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime desde,
                                                   @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime hasta,
                                                   @PathVariable Integer geograficaId, @PathVariable Boolean todos);

    @GetMapping("/{proveedorMovimientoId}")
    ProveedorMovimientoDto findByProveedorMovimientoId(@PathVariable Long proveedorMovimientoId);

    @GetMapping("/ordenPago/{prefijo}/{numeroComprobante}")
    ProveedorMovimientoDto findByOrdenPago(@PathVariable Integer prefijo, @PathVariable Long numeroComprobante);

    @GetMapping("/lastOrdenPago/{prefijo}")
    ProveedorMovimientoDto findLastOrdenPago(@PathVariable Integer prefijo);

    @PostMapping("/")
    ProveedorMovimientoDto add(@RequestBody ProveedorMovimientoDto proveedorMovimiento);

}
