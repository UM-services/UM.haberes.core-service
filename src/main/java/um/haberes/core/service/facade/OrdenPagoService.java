package um.haberes.core.service.facade;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.core.exception.AcreditacionPagoException;
import um.haberes.core.exception.extern.ProveedorMovimientoException;
import um.haberes.core.kotlin.model.internal.OrdenPagoRequest;
import um.haberes.core.kotlin.model.AcreditacionPago;
import um.haberes.core.kotlin.model.extern.EjercicioDto;
import um.haberes.core.kotlin.model.extern.ProveedorMovimientoDto;
import um.haberes.core.service.AcreditacionPagoService;
import um.haberes.core.service.extern.EjercicioService;
import um.haberes.core.service.extern.ProveedorMovimientoService;

import java.math.BigDecimal;

@Service
public class OrdenPagoService {

    @Autowired
    private EjercicioService ejercicioService;

    @Autowired
    private AcreditacionPagoService acreditacionPagoService;

    @Autowired
    private ProveedorMovimientoService proveedorMovimientoService;

    @Transactional
    public Boolean generateOrdenPago(OrdenPagoRequest ordenPagoRequest) {
        if (ordenPagoRequest.getIndividual()) {
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
