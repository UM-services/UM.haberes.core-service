package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;

public interface GetLiquidacionesByLegajoIdsAndPeriodoUseCase {

    List<Liquidacion> getLiquidacionesByLegajoIdsAndPeriodo(List<Long> legajoIds, Integer anho, Integer mes);
}
