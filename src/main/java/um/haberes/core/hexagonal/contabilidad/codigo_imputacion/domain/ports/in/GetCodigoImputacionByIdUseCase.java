package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.model.CodigoImputacion;

public interface GetCodigoImputacionByIdUseCase {

    Optional<CodigoImputacion> getCodigoImputacionById(Long codigoImputacionId);
}
