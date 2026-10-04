package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.model.CodigoImputacion;

public interface GetCodigoImputacionByUniqueUseCase {

    Optional<CodigoImputacion> getCodigoImputacionByUnique(Integer dependenciaId, Integer facultadId,
            Integer geograficaId, Integer codigoId);
}
