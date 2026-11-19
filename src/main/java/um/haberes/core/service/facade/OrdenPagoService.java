package um.haberes.core.service.facade;

import java.math.BigDecimal;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.application.exception.AcreditacionPagoException;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.application.service.AcreditacionPagoService;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.model.AcreditacionPago;
import um.haberes.core.model.extern.EjercicioDto;
import um.haberes.core.model.extern.ProveedorMovimientoDto;
import um.haberes.core.model.internal.OrdenPagoRequest;
import um.haberes.core.service.extern.EjercicioService;
import um.haberes.core.service.extern.ProveedorMovimientoService;

/**
 * Réplica de {@code clsCtlOrdenPago.generateOrden} del legacy: reutiliza la orden de pago del
 * período/fecha y, si no existe, crea el siguiente comprobante del ejercicio y guarda la
 * {@code acreditacion_pago} con los totales acumulados de cada archivo.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrdenPagoService {

    private static final Integer COMPROBANTE_ID_ORDEN_PAGO = 6;
    private static final String CONCEPTO_PREFIX = "Sueldos mes ";
    private static final String BENEFICIARIO = "Personal Administrativo y Docente";

    private final EjercicioService ejercicioService;
    private final AcreditacionPagoService acreditacionPagoService;
    private final ProveedorMovimientoService proveedorMovimientoService;

    @Transactional
    public Boolean generateOrdenPago(OrdenPagoRequest ordenPagoRequest) {
        if (ordenPagoRequest.isIndividual()) {
            return true;
        }
        EjercicioDto ejercicioDto = ejercicioService.findByFecha(ordenPagoRequest.getFechaPago());
        AcreditacionPago acreditacionPago = null;
        ProveedorMovimientoDto proveedorMovimientoDto = null;
        try {
            acreditacionPago = acreditacionPagoService.findByUnique(ordenPagoRequest.getAnho(),
                    ordenPagoRequest.getMes(), ordenPagoRequest.getFechaPago());
            if (acreditacionPago.getNumeroComprobantePago() != null
                    && acreditacionPago.getNumeroComprobantePago() > 0) {
                proveedorMovimientoDto = findOrdenPago(acreditacionPago.getPuntoVentaPago(),
                        acreditacionPago.getNumeroComprobantePago());
            }
        } catch (AcreditacionPagoException e) {
            log.debug("Sin acreditación de pago para {}/{}", ordenPagoRequest.getMes(), ordenPagoRequest.getAnho());
        }

        if (proveedorMovimientoDto == null) {
            long numeroComprobante = 1L;
            try {
                ProveedorMovimientoDto ultimo = proveedorMovimientoService
                        .findLastOrdenPago(ejercicioDto.getEjercicioId());
                if (ultimo != null) {
                    numeroComprobante = ultimo.getNumeroComprobante() + 1;
                }
            } catch (RuntimeException e) {
                log.warn("No se pudo obtener la última orden de pago del ejercicio {}: {}",
                        ejercicioDto.getEjercicioId(), e.getMessage());
            }
            proveedorMovimientoDto = new ProveedorMovimientoDto(null, null, BENEFICIARIO,
                    COMPROBANTE_ID_ORDEN_PAGO, ordenPagoRequest.getFechaPago(), ordenPagoRequest.getFechaPago(),
                    ejercicioDto.getEjercicioId(), numeroComprobante, BigDecimal.ZERO, BigDecimal.ZERO,
                    BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, null, 0,
                    CONCEPTO_PREFIX + String.format("%02d", ordenPagoRequest.getMes()) + "/"
                            + ordenPagoRequest.getAnho(),
                    null, (byte) 0, (byte) 0, null, null);
            proveedorMovimientoDto = proveedorMovimientoService.add(proveedorMovimientoDto);
        }

        if (acreditacionPago == null) {
            acreditacionPago = AcreditacionPago.builder()
                    .anho(ordenPagoRequest.getAnho())
                    .mes(ordenPagoRequest.getMes())
                    .fechaPago(ordenPagoRequest.getFechaPago())
                    .totalSantander(BigDecimal.ZERO)
                    .totalOtrosBancos(BigDecimal.ZERO)
                    .build();
        }
        acreditacionPago.setComprobanteIdPago(proveedorMovimientoDto.getComprobanteId());
        acreditacionPago.setPuntoVentaPago(proveedorMovimientoDto.getPrefijo());
        acreditacionPago.setNumeroComprobantePago(proveedorMovimientoDto.getNumeroComprobante());
        if (positivo(ordenPagoRequest.getTotalSantander())) {
            acreditacionPago.setTotalSantander(ordenPagoRequest.getTotalSantander());
        }
        if (positivo(ordenPagoRequest.getTotalOtrosBancos())) {
            acreditacionPago.setTotalOtrosBancos(ordenPagoRequest.getTotalOtrosBancos());
        }
        if (acreditacionPago.getAcreditacionPagoId() == null) {
            acreditacionPagoService.add(acreditacionPago);
        } else {
            acreditacionPagoService.update(acreditacionPago, acreditacionPago.getAcreditacionPagoId());
        }
        return true;
    }

    private ProveedorMovimientoDto findOrdenPago(Integer puntoVenta, Long numeroComprobante) {
        try {
            return proveedorMovimientoService.findByOrdenPago(puntoVenta, numeroComprobante);
        } catch (RuntimeException e) {
            log.warn("No se pudo recuperar la orden de pago {}-{}: {}", puntoVenta, numeroComprobante,
                    e.getMessage());
            return null;
        }
    }

    private static boolean positivo(BigDecimal valor) {
        return valor != null && valor.compareTo(BigDecimal.ZERO) > 0;
    }
}
