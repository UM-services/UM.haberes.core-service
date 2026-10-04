package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.model.CodigoImputacion;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.infrastructure.web.dto.CodigoImputacionRequest;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.infrastructure.web.dto.CodigoImputacionResponse;

@Component
public class CodigoImputacionDtoMapper {

    public CodigoImputacion toDomain(CodigoImputacionRequest request) {
        if (request == null) {
            return null;
        }
        return CodigoImputacion.builder()
                .dependenciaId(request.getDependenciaId())
                .facultadId(request.getFacultadId())
                .geograficaId(request.getGeograficaId())
                .codigoId(request.getCodigoId())
                .cuentaSueldosDocente(request.getCuentaSueldosDocente())
                .cuentaAportesDocente(request.getCuentaAportesDocente())
                .cuentaSueldosNoDocente(request.getCuentaSueldosNoDocente())
                .cuentaAportesNoDocente(request.getCuentaAportesNoDocente())
                .build();
    }

    public CodigoImputacionResponse toResponse(CodigoImputacion domain) {
        if (domain == null) {
            return null;
        }
        return CodigoImputacionResponse.builder()
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
}
