package um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.model.LegajoCargoClaseImputacion;

public interface FindLegajoCargoClaseImputacionesByLegajoUseCase {

    List<LegajoCargoClaseImputacion> findLegajoCargoClaseImputacionesByLegajo(Long legajoId, Integer anho,
            Integer mes);
}
