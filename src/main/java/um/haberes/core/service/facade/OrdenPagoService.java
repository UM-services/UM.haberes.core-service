package um.haberes.core.service.facade;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.application.exception.AcreditacionPagoException;
import um.haberes.core.exception.extern.ProveedorMovimientoException;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.model.AcreditacionPago;
import um.haberes.core.model.internal.OrdenPagoRequest;
import um.haberes.core.model.extern.EjercicioDto;
import um.haberes.core.model.extern.ProveedorMovimientoDto;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.application.service.AcreditacionPagoService;
import um.haberes.core.service.extern.EjercicioService;
import um.haberes.core.service.extern.ProveedorMovimientoService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrdenPagoService {

    private final EjercicioService ejercicioService;
    private final AcreditacionPagoService acreditacionPagoService;
    private final ProveedorMovimientoService proveedorMovimientoService;

    @Transactional
    public Boolean generateOrdenPago(OrdenPagoRequest ordenPagoRequest) {
        if (ordenPagoRequest.isIndividual()) {
            return true;
        }
        EjercicioDto ejercicioDto = ejercicioService.findByFecha(ordenPagoRequest.getFechaPago());
        ProveedorMovimientoDto proveedorMovimientoDto = null;
        AcreditacionPago acreditacionPago = null;
        try {
            acreditacionPago = acreditacionPagoService.findByUnique(ordenPagoRequest.getAnho(), ordenPagoRequest.getMes(), ordenPagoRequest.getFechaPago());
            proveedorMovimientoDto = proveedorMovimientoService.findByOrdenPago(acreditacionPago.getPuntoVentaPago(), acreditacionPago.getNumeroComprobantePago());
        } catch (AcreditacionPagoException e) {
            Long ordenPago = 0L;
            try {
                proveedorMovimientoDto = proveedorMovimientoService.findLastOrdenPago(ejercicioDto.getEjercicioId());
                ordenPago = proveedorMovimientoDto.getNumeroComprobante();
            } catch (ProveedorMovimientoException p) {

            }
            proveedorMovimientoDto = new ProveedorMovimientoDto(null, null, "Personal Administrativo y Docente", 6, ordenPagoRequest.getFechaPago(), ordenPagoRequest.getFechaPago(), ejercicioDto.getEjercicioId(), 1 + ordenPago, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, null, 0, "Sueldos mes " + ordenPagoRequest.getMes() + "/" + ordenPagoRequest.getAnho(), null, (byte) 0, (byte) 0, null, null);
//            proveedorMovimiento = proveedorMovimientoService.add(proveedorMovimiento);
        }
        return false;
    }

}
