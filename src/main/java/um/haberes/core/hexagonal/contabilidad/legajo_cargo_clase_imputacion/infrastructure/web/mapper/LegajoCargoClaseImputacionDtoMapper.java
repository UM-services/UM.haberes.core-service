package um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.model.LegajoCargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.infrastructure.web.dto.LegajoCargoClaseImputacionResponse;

@Component
public class LegajoCargoClaseImputacionDtoMapper {

    public LegajoCargoClaseImputacionResponse toResponse(LegajoCargoClaseImputacion domain) {
        if (domain == null) {
            return null;
        }
        return LegajoCargoClaseImputacionResponse.builder()
                .legajoCargoClaseImputacionId(domain.getLegajoCargoClaseImputacionId())
                .legajoId(domain.getLegajoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .dependenciaId(domain.getDependenciaId())
                .facultadId(domain.getFacultadId())
                .geograficaId(domain.getGeograficaId())
                .cargoClaseId(domain.getCargoClaseId())
                .cuentaSueldos(domain.getCuentaSueldos())
                .basico(domain.getBasico())
                .antiguedad(domain.getAntiguedad())
                .cuentaAportes(domain.getCuentaAportes())
                .build();
    }
}
