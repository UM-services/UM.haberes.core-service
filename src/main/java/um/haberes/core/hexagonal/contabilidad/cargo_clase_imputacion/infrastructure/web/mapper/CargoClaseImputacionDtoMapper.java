package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.domain.model.CargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.web.dto.CargoClaseImputacionRequest;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.infrastructure.web.dto.CargoClaseImputacionResponse;

@Component
public class CargoClaseImputacionDtoMapper {

    public CargoClaseImputacion toDomain(CargoClaseImputacionRequest request) {
        if (request == null) {
            return null;
        }
        return CargoClaseImputacion.builder()
                .dependenciaId(request.getDependenciaId())
                .facultadId(request.getFacultadId())
                .geograficaId(request.getGeograficaId())
                .cargoClaseId(request.getCargoClaseId())
                .cuentaSueldos(request.getCuentaSueldos())
                .cuentaAportes(request.getCuentaAportes())
                .build();
    }

    public CargoClaseImputacionResponse toResponse(CargoClaseImputacion domain) {
        if (domain == null) {
            return null;
        }
        return CargoClaseImputacionResponse.builder()
                .cargoClaseImputacionId(domain.getCargoClaseImputacionId())
                .dependenciaId(domain.getDependenciaId())
                .facultadId(domain.getFacultadId())
                .geograficaId(domain.getGeograficaId())
                .cargoClaseId(domain.getCargoClaseId())
                .cuentaSueldos(domain.getCuentaSueldos())
                .cuentaAportes(domain.getCuentaAportes())
                .build();
    }
}
