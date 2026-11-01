package um.haberes.core.hexagonal.contabilidad.categoria_imputacion.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.domain.model.CategoriaImputacion;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.infrastructure.web.dto.CategoriaImputacionRequest;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.infrastructure.web.dto.CategoriaImputacionResponse;

@Component
public class CategoriaImputacionDtoMapper {

    public CategoriaImputacion toDomain(CategoriaImputacionRequest request) {
        if (request == null) {
            return null;
        }
        return CategoriaImputacion.builder()
                .dependenciaId(request.getDependenciaId())
                .facultadId(request.getFacultadId())
                .geograficaId(request.getGeograficaId())
                .categoriaId(request.getCategoriaId())
                .cuentaSueldos(request.getCuentaSueldos())
                .cuentaAportes(request.getCuentaAportes())
                .build();
    }

    public CategoriaImputacionResponse toResponse(CategoriaImputacion domain) {
        if (domain == null) {
            return null;
        }
        return CategoriaImputacionResponse.builder()
                .categoriaImputacionId(domain.getCategoriaImputacionId())
                .dependenciaId(domain.getDependenciaId())
                .facultadId(domain.getFacultadId())
                .geograficaId(domain.getGeograficaId())
                .categoriaId(domain.getCategoriaId())
                .cuentaSueldos(domain.getCuentaSueldos())
                .cuentaAportes(domain.getCuentaAportes())
                .build();
    }
}
