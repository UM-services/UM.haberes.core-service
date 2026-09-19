package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.model.CargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.persistence.entity.CargoClaseImputacionEntity;

@Component
public class CargoClaseImputacionMapper {

    public CargoClaseImputacionEntity toEntity(CargoClaseImputacion domain) {
        if (domain == null) {
            return null;
        }
        return CargoClaseImputacionEntity.builder()
                .cargoClaseImputacionId(domain.getCargoClaseImputacionId())
                .dependenciaId(domain.getDependenciaId())
                .facultadId(domain.getFacultadId())
                .geograficaId(domain.getGeograficaId())
                .cargoClaseId(domain.getCargoClaseId())
                .cuentaSueldos(domain.getCuentaSueldos())
                .cuentaAportes(domain.getCuentaAportes())
                .build();
    }

    public CargoClaseImputacion toDomain(CargoClaseImputacionEntity entity) {
        if (entity == null) {
            return null;
        }
        return CargoClaseImputacion.builder()
                .cargoClaseImputacionId(entity.getCargoClaseImputacionId())
                .dependenciaId(entity.getDependenciaId())
                .facultadId(entity.getFacultadId())
                .geograficaId(entity.getGeograficaId())
                .cargoClaseId(entity.getCargoClaseId())
                .cuentaSueldos(entity.getCuentaSueldos())
                .cuentaAportes(entity.getCuentaAportes())
                .build();
    }
}
