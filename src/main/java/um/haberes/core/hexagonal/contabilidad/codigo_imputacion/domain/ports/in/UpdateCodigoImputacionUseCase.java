package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.model.CodigoImputacion;

public interface UpdateCodigoImputacionUseCase {

    Optional<CodigoImputacion> updateCodigoImputacion(Long codigoImputacionId, CodigoImputacion codigoImputacion);
}
