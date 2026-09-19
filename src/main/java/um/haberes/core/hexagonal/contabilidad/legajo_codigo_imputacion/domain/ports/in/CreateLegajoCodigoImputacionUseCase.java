package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.in;

import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.model.LegajoCodigoImputacion;

public interface CreateLegajoCodigoImputacionUseCase {

    LegajoCodigoImputacion createLegajoCodigoImputacion(LegajoCodigoImputacion legajoCodigoImputacion);
}
