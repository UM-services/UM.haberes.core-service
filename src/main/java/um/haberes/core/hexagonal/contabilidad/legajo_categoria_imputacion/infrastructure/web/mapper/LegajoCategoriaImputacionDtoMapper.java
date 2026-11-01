package um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.model.LegajoCategoriaImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.infrastructure.web.dto.LegajoCategoriaImputacionResponse;

@Component
public class LegajoCategoriaImputacionDtoMapper {

    public LegajoCategoriaImputacionResponse toResponse(LegajoCategoriaImputacion domain) {
        if (domain == null) {
            return null;
        }
        return LegajoCategoriaImputacionResponse.builder()
                .legajoCategoriaImputacionId(domain.getLegajoCategoriaImputacionId())
                .legajoId(domain.getLegajoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .dependenciaId(domain.getDependenciaId())
                .facultadId(domain.getFacultadId())
                .geograficaId(domain.getGeograficaId())
                .categoriaId(domain.getCategoriaId())
                .cuentaSueldos(domain.getCuentaSueldos())
                .basico(domain.getBasico())
                .antiguedad(domain.getAntiguedad())
                .cuentaAportes(domain.getCuentaAportes())
                .build();
    }
}
