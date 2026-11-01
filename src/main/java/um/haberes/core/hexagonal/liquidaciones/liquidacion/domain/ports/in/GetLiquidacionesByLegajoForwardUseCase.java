package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.LiquidacionPeriodoForward;

public interface GetLiquidacionesByLegajoForwardUseCase {

    List<LiquidacionPeriodoForward> getLiquidacionesByLegajoForward(Long legajoId, Integer anho, Integer mes);
}
