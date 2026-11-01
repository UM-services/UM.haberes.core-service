package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;

public interface GetLiquidacionesByPeriodoAndLegajoIdsUseCase {

    List<Liquidacion> getLiquidacionesByPeriodoAndLegajoIds(Integer anho, Integer mes, List<Long> legajoIds);
}
