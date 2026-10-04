package um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.ports.in;

import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.model.LegajoCargoClaseImputacion;

public interface CreateLegajoCargoClaseImputacionUseCase {

    LegajoCargoClaseImputacion createLegajoCargoClaseImputacion(
            LegajoCargoClaseImputacion legajoCargoClaseImputacion);
}
