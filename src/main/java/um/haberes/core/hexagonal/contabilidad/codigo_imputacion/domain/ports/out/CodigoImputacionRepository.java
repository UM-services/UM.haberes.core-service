package um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.domain.model.CodigoImputacion;

public interface CodigoImputacionRepository {

    CodigoImputacion create(CodigoImputacion codigoImputacion);

    Optional<CodigoImputacion> findById(Long codigoImputacionId);

    Optional<CodigoImputacion> findByUnique(Integer dependenciaId, Integer facultadId, Integer geograficaId,
            Integer codigoId);

    List<CodigoImputacion> findAll();

    Optional<CodigoImputacion> update(Long codigoImputacionId, CodigoImputacion codigoImputacion);
}
