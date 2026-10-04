package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.model.CodigoImputacion;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.infrastructure.persistence.entity.CodigoImputacionEntity;

@Component
public class CodigoImputacionMapper {

    public CodigoImputacionEntity toEntity(CodigoImputacion domain) {
        if (domain == null) {
            return null;
        }
        return CodigoImputacionEntity.builder()
                .codigoImputacionId(domain.getCodigoImputacionId())
                .dependenciaId(domain.getDependenciaId())
                .facultadId(domain.getFacultadId())
                .geograficaId(domain.getGeograficaId())
                .codigoId(domain.getCodigoId())
                .cuentaSueldosDocente(domain.getCuentaSueldosDocente())
                .cuentaAportesDocente(domain.getCuentaAportesDocente())
                .cuentaSueldosNoDocente(domain.getCuentaSueldosNoDocente())
                .cuentaAportesNoDocente(domain.getCuentaAportesNoDocente())
                .build();
    }

    public CodigoImputacion toDomain(CodigoImputacionEntity entity) {
        if (entity == null) {
            return null;
        }
        return CodigoImputacion.builder()
                .codigoImputacionId(entity.getCodigoImputacionId())
                .dependenciaId(entity.getDependenciaId())
                .facultadId(entity.getFacultadId())
                .geograficaId(entity.getGeograficaId())
                .codigoId(entity.getCodigoId())
                .cuentaSueldosDocente(entity.getCuentaSueldosDocente())
                .cuentaAportesDocente(entity.getCuentaAportesDocente())
                .cuentaSueldosNoDocente(entity.getCuentaSueldosNoDocente())
                .cuentaAportesNoDocente(entity.getCuentaAportesNoDocente())
                .build();
    }
}
