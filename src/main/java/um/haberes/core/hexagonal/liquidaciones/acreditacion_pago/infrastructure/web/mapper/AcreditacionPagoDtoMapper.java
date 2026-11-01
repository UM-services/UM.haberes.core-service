package um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.model.AcreditacionPago;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.infrastructure.web.dto.AcreditacionPagoRequest;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.infrastructure.web.dto.AcreditacionPagoResponse;

@Component
public class AcreditacionPagoDtoMapper {

    public AcreditacionPago toDomain(AcreditacionPagoRequest request) {
        if (request == null) {
            return null;
        }
        return AcreditacionPago.builder()
                .anho(request.getAnho())
                .mes(request.getMes())
                .fechaPago(request.getFechaPago())
                .totalSantander(request.getTotalSantander())
                .totalOtrosBancos(request.getTotalOtrosBancos())
                .comprobanteIdPago(request.getComprobanteIdPago())
                .puntoVentaPago(request.getPuntoVentaPago())
                .numeroComprobantePago(request.getNumeroComprobantePago())
                .build();
    }

    public AcreditacionPagoResponse toResponse(AcreditacionPago domain) {
        if (domain == null) {
            return null;
        }
        return AcreditacionPagoResponse.builder()
                .acreditacionPagoId(domain.getAcreditacionPagoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .fechaPago(domain.getFechaPago())
                .totalSantander(domain.getTotalSantander())
                .totalOtrosBancos(domain.getTotalOtrosBancos())
                .comprobanteIdPago(domain.getComprobanteIdPago())
                .puntoVentaPago(domain.getPuntoVentaPago())
                .numeroComprobantePago(domain.getNumeroComprobantePago())
                .build();
    }
}
