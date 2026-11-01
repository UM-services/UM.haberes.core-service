package um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.model.LegajoCargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.infrastructure.persistence.entity.LegajoCargoClaseImputacionEntity;

@Component
public class LegajoCargoClaseImputacionMapper {

    public LegajoCargoClaseImputacionEntity toEntity(LegajoCargoClaseImputacion domain) {
        if (domain == null) {
            return null;
        }
        LegajoCargoClaseImputacionEntity.LegajoCargoClaseImputacionEntityBuilder builder = LegajoCargoClaseImputacionEntity
                .builder()
                .legajoCargoClaseImputacionId(domain.getLegajoCargoClaseImputacionId())
                .legajoId(domain.getLegajoId())
                .dependenciaId(domain.getDependenciaId())
                .facultadId(domain.getFacultadId())
                .geograficaId(domain.getGeograficaId())
                .cargoClaseId(domain.getCargoClaseId())
                .cuentaSueldos(domain.getCuentaSueldos())
                .cuentaAportes(domain.getCuentaAportes());
        if (domain.getAnho() != null) {
            builder.anho(domain.getAnho());
        }
        if (domain.getMes() != null) {
            builder.mes(domain.getMes());
        }
        if (domain.getBasico() != null) {
            builder.basico(domain.getBasico());
        }
        if (domain.getAntiguedad() != null) {
            builder.antiguedad(domain.getAntiguedad());
        }
        return builder.build();
    }

    public LegajoCargoClaseImputacion toDomain(LegajoCargoClaseImputacionEntity entity) {
        if (entity == null) {
            return null;
        }
        return LegajoCargoClaseImputacion.builder()
                .legajoCargoClaseImputacionId(entity.getLegajoCargoClaseImputacionId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .dependenciaId(entity.getDependenciaId())
                .facultadId(entity.getFacultadId())
                .geograficaId(entity.getGeograficaId())
                .cargoClaseId(entity.getCargoClaseId())
                .cuentaSueldos(entity.getCuentaSueldos())
                .basico(entity.getBasico())
                .antiguedad(entity.getAntiguedad())
                .cuentaAportes(entity.getCuentaAportes())
                .build();
    }
}
