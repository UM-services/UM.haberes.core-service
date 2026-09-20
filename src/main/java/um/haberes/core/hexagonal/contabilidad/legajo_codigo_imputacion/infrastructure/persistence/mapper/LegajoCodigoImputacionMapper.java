package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.model.LegajoCodigoImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.infrastructure.persistence.entity.LegajoCodigoImputacionEntity;

@Component
public class LegajoCodigoImputacionMapper {

    public LegajoCodigoImputacionEntity toEntity(LegajoCodigoImputacion domain) {
        if (domain == null) {
            return null;
        }
        LegajoCodigoImputacionEntity.LegajoCodigoImputacionEntityBuilder builder = LegajoCodigoImputacionEntity
                .builder()
                .legajoCodigoImputacionId(domain.getLegajoCodigoImputacionId())
                .legajoId(domain.getLegajoId())
                .dependenciaId(domain.getDependenciaId())
                .facultadId(domain.getFacultadId())
                .geograficaId(domain.getGeograficaId())
                .codigoId(domain.getCodigoId())
                .cuentaSueldos(domain.getCuentaSueldos())
                .cuentaAportes(domain.getCuentaAportes());
        if (domain.getAnho() != null) {
            builder.anho(domain.getAnho());
        }
        if (domain.getMes() != null) {
            builder.mes(domain.getMes());
        }
        if (domain.getImporte() != null) {
            builder.importe(domain.getImporte());
        }
        return builder.build();
    }

    public LegajoCodigoImputacion toDomain(LegajoCodigoImputacionEntity entity) {
        if (entity == null) {
            return null;
        }
        return LegajoCodigoImputacion.builder()
                .legajoCodigoImputacionId(entity.getLegajoCodigoImputacionId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .dependenciaId(entity.getDependenciaId())
                .facultadId(entity.getFacultadId())
                .geograficaId(entity.getGeograficaId())
                .codigoId(entity.getCodigoId())
                .cuentaSueldos(entity.getCuentaSueldos())
                .importe(entity.getImporte())
                .cuentaAportes(entity.getCuentaAportes())
                .build();
    }
}
