package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.model.LegajoCodigoImputacion;

public interface LegajoCodigoImputacionRepository {

    LegajoCodigoImputacion create(LegajoCodigoImputacion legajoCodigoImputacion);

    List<LegajoCodigoImputacion> findByLegajo(Long legajoId, Integer anho, Integer mes);

    List<LegajoCodigoImputacion> findByLegajoAndCodigos(Long legajoId, Integer anho, Integer mes,
            List<Integer> codigoIds);

    void deleteAllByLegajo(Long legajoId, Integer anho, Integer mes);

    void deleteAllByPeriodo(Integer anho, Integer mes);
}
