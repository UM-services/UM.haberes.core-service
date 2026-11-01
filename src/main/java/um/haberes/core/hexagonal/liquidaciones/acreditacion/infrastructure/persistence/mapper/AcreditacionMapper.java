package um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.persistence.entity.AcreditacionEntity;

@Component
public class AcreditacionMapper {

    public AcreditacionEntity toEntity(Acreditacion domain) {
        if (domain == null) {
            return null;
        }
        AcreditacionEntity.AcreditacionEntityBuilder builder = AcreditacionEntity.builder()
                .acreditacionId(domain.getAcreditacionId());
        if (domain.getAnho() != null) {
            builder.anho(domain.getAnho());
        }
        if (domain.getMes() != null) {
            builder.mes(domain.getMes());
        }
        if (domain.getAcreditado() != null) {
            builder.acreditado(domain.getAcreditado());
        }
        builder.limiteNovedades(domain.getLimiteNovedades());
        builder.fechaContable(domain.getFechaContable());
        builder.ordenContable(domain.getOrdenContable());
        if (domain.getSueldosOriginal() != null) {
            builder.sueldosOriginal(domain.getSueldosOriginal());
        }
        if (domain.getSueldosAjustados() != null) {
            builder.sueldosAjustados(domain.getSueldosAjustados());
        }
        if (domain.getContribucionesPatronales() != null) {
            builder.contribucionesPatronales(domain.getContribucionesPatronales());
        }
        return builder.build();
    }

    public Acreditacion toDomain(AcreditacionEntity entity) {
        if (entity == null) {
            return null;
        }
        return Acreditacion.builder()
                .acreditacionId(entity.getAcreditacionId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .acreditado(entity.getAcreditado())
                .limiteNovedades(entity.getLimiteNovedades())
                .fechaContable(entity.getFechaContable())
                .ordenContable(entity.getOrdenContable())
                .sueldosOriginal(entity.getSueldosOriginal())
                .sueldosAjustados(entity.getSueldosAjustados())
                .contribucionesPatronales(entity.getContribucionesPatronales())
                .build();
    }
}
