package um.haberes.rest.service.facade;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.rest.exception.AcreditacionPagoException;
import um.haberes.rest.exception.extern.ProveedorMovimientoException;
import um.haberes.rest.kotlin.internal.OrdenPagoRequest;
import um.haberes.rest.kotlin.model.extern.Ejercicio;
import um.haberes.rest.kotlin.model.extern.ProveedorMovimiento;
import um.haberes.rest.model.AcreditacionPago;
import um.haberes.rest.service.AcreditacionPagoService;
import um.haberes.rest.service.extern.EjercicioService;
import um.haberes.rest.service.extern.ProveedorMovimientoService;

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
        Ejercicio ejercicio = ejercicioService.findByFecha(ordenPagoRequest.getFechaPago());
        ProveedorMovimiento proveedorMovimiento = null;
        AcreditacionPago acreditacionPago = null;
        try {
            acreditacionPago = acreditacionPagoService.findByUnique(ordenPagoRequest.getAnho(), ordenPagoRequest.getMes(), ordenPagoRequest.getFechaPago());
            proveedorMovimiento = proveedorMovimientoService.findByOrdenPago(acreditacionPago.getPuntoVentaPago(), acreditacionPago.getNumeroComprobantePago());
        } catch (AcreditacionPagoException e) {
            Long ordenPago = 0L;
            try {
                proveedorMovimiento = proveedorMovimientoService.findLastOrdenPago(ejercicio.getEjercicioId());
                ordenPago = proveedorMovimiento.getNumeroComprobante();
            } catch (ProveedorMovimientoException p) {

            }
            proveedorMovimiento = new ProveedorMovimiento(null, null, "Personal Administrativo y Docente", 6, ordenPagoRequest.getFechaPago(), ordenPagoRequest.getFechaPago(), ejercicio.getEjercicioId(), 1 + ordenPago, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, null, 0, "Sueldos mes " + ordenPagoRequest.getMes() + "/" + ordenPagoRequest.getAnho(), null, (byte) 0, (byte) 0, null, null);
//            proveedorMovimiento = proveedorMovimientoService.add(proveedorMovimiento);
        }
        return false;
    }

}
