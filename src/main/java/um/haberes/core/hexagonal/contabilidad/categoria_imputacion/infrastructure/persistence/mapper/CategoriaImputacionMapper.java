package um.haberes.core.hexagonal.contabilidad.categoria_imputacion.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.model.CategoriaImputacion;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.infrastructure.persistence.entity.CategoriaImputacionEntity;

@Component
public class CategoriaImputacionMapper {

    public CategoriaImputacionEntity toEntity(CategoriaImputacion domain) {
        if (domain == null) {
            return null;
        }
        return CategoriaImputacionEntity.builder()
                .categoriaImputacionId(domain.getCategoriaImputacionId())
                .dependenciaId(domain.getDependenciaId())
                .facultadId(domain.getFacultadId())
                .geograficaId(domain.getGeograficaId())
                .categoriaId(domain.getCategoriaId())
                .cuentaSueldos(domain.getCuentaSueldos())
                .cuentaAportes(domain.getCuentaAportes())
                .build();
    }

    public CategoriaImputacion toDomain(CategoriaImputacionEntity entity) {
        if (entity == null) {
            return null;
        }
        return CategoriaImputacion.builder()
                .categoriaImputacionId(entity.getCategoriaImputacionId())
                .dependenciaId(entity.getDependenciaId())
                .facultadId(entity.getFacultadId())
                .geograficaId(entity.getGeograficaId())
                .categoriaId(entity.getCategoriaId())
                .cuentaSueldos(entity.getCuentaSueldos())
                .cuentaAportes(entity.getCuentaAportes())
                .build();
    }
}
