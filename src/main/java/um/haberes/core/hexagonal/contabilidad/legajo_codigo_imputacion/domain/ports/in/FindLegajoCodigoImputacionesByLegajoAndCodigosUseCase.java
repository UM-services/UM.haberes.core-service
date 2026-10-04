package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.model.LegajoCodigoImputacion;

public interface FindLegajoCodigoImputacionesByLegajoAndCodigosUseCase {

    List<LegajoCodigoImputacion> findLegajoCodigoImputacionesByLegajoAndCodigos(Long legajoId, Integer anho,
            Integer mes, List<Integer> codigoIds);
}
