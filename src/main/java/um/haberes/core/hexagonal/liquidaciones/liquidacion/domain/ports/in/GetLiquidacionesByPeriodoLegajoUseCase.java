package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;

public interface GetLiquidacionesByPeriodoLegajoUseCase {

    List<Liquidacion> getLiquidacionesByPeriodoLegajo(Integer anho, Integer mes, Long legajoId, Integer limit);
}
