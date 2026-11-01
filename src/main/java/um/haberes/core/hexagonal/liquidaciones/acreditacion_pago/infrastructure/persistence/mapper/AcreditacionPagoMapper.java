package um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.model.AcreditacionPago;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.infrastructure.persistence.entity.AcreditacionPagoEntity;

@Component
public class AcreditacionPagoMapper {

    public AcreditacionPagoEntity toEntity(AcreditacionPago domain) {
        if (domain == null) {
            return null;
        }
        AcreditacionPagoEntity.AcreditacionPagoEntityBuilder builder = AcreditacionPagoEntity.builder()
                .acreditacionPagoId(domain.getAcreditacionPagoId())
                .fechaPago(domain.getFechaPago())
                .comprobanteIdPago(domain.getComprobanteIdPago())
                .puntoVentaPago(domain.getPuntoVentaPago())
                .numeroComprobantePago(domain.getNumeroComprobantePago());
        if (domain.getAnho() != null) {
            builder.anho(domain.getAnho());
        }
        if (domain.getMes() != null) {
            builder.mes(domain.getMes());
        }
        if (domain.getTotalSantander() != null) {
            builder.totalSantander(domain.getTotalSantander());
        }
        if (domain.getTotalOtrosBancos() != null) {
            builder.totalOtrosBancos(domain.getTotalOtrosBancos());
        }
        return builder.build();
    }

    public AcreditacionPago toDomain(AcreditacionPagoEntity entity) {
        if (entity == null) {
            return null;
        }
        return AcreditacionPago.builder()
                .acreditacionPagoId(entity.getAcreditacionPagoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .fechaPago(entity.getFechaPago())
                .totalSantander(entity.getTotalSantander())
                .totalOtrosBancos(entity.getTotalOtrosBancos())
                .comprobanteIdPago(entity.getComprobanteIdPago())
                .puntoVentaPago(entity.getPuntoVentaPago())
                .numeroComprobantePago(entity.getNumeroComprobantePago())
                .build();
    }
}
