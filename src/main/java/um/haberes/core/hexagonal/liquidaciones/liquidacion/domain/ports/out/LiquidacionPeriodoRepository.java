package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.LiquidacionPeriodoForward;

public interface LiquidacionPeriodoRepository {

    List<LiquidacionPeriodoForward> findAllByLegajoIdForward(Long legajoId, Integer anho, Integer mes);
}
