package um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.model.LegajoCategoriaImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.infrastructure.persistence.entity.LegajoCategoriaImputacionEntity;

@Component
public class LegajoCategoriaImputacionMapper {

    public LegajoCategoriaImputacionEntity toEntity(LegajoCategoriaImputacion domain) {
        if (domain == null) {
            return null;
        }
        LegajoCategoriaImputacionEntity.LegajoCategoriaImputacionEntityBuilder builder = LegajoCategoriaImputacionEntity
                .builder()
                .legajoCategoriaImputacionId(domain.getLegajoCategoriaImputacionId())
                .legajoId(domain.getLegajoId())
                .dependenciaId(domain.getDependenciaId())
                .facultadId(domain.getFacultadId())
                .geograficaId(domain.getGeograficaId())
                .categoriaId(domain.getCategoriaId())
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

    public LegajoCategoriaImputacion toDomain(LegajoCategoriaImputacionEntity entity) {
        if (entity == null) {
            return null;
        }
        return LegajoCategoriaImputacion.builder()
                .legajoCategoriaImputacionId(entity.getLegajoCategoriaImputacionId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .dependenciaId(entity.getDependenciaId())
                .facultadId(entity.getFacultadId())
                .geograficaId(entity.getGeograficaId())
                .categoriaId(entity.getCategoriaId())
                .cuentaSueldos(entity.getCuentaSueldos())
                .basico(entity.getBasico())
                .antiguedad(entity.getAntiguedad())
                .cuentaAportes(entity.getCuentaAportes())
                .build();
    }
}
