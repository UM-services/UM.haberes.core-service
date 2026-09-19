package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.in;

import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.model.CodigoImputacion;

public interface CreateCodigoImputacionUseCase {

    CodigoImputacion createCodigoImputacion(CodigoImputacion codigoImputacion);
}
