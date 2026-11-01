package um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.model.LegajoCategoriaImputacion;

public interface FindLegajoCategoriaImputacionesByLegajoUseCase {

    List<LegajoCategoriaImputacion> findLegajoCategoriaImputacionesByLegajo(Long legajoId, Integer anho,
            Integer mes);
}
