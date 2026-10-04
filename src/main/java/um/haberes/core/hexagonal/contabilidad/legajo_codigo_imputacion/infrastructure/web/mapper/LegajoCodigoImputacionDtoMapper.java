package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.model.LegajoCodigoImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.infrastructure.web.dto.LegajoCodigoImputacionResponse;

@Component
public class LegajoCodigoImputacionDtoMapper {

    public LegajoCodigoImputacionResponse toResponse(LegajoCodigoImputacion domain) {
        if (domain == null) {
            return null;
        }
        return LegajoCodigoImputacionResponse.builder()
                .legajoCodigoImputacionId(domain.getLegajoCodigoImputacionId())
                .legajoId(domain.getLegajoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .dependenciaId(domain.getDependenciaId())
                .facultadId(domain.getFacultadId())
                .geograficaId(domain.getGeograficaId())
                .codigoId(domain.getCodigoId())
                .cuentaSueldos(domain.getCuentaSueldos())
                .importe(domain.getImporte())
                .cuentaAportes(domain.getCuentaAportes())
                .build();
    }
}
