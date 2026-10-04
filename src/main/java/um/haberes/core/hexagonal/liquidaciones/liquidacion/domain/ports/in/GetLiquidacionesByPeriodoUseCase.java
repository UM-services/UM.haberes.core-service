package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;

public interface GetLiquidacionesByPeriodoUseCase {

    List<Liquidacion> getLiquidacionesByPeriodo(Integer anho, Integer mes, Integer limit);
}
