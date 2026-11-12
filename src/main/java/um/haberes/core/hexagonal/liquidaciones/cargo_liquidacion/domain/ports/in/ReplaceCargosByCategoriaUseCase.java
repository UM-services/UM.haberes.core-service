package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;

/**
 * Reemplazo atómico de los cargos de un conjunto de categorías para un período (o para un
 * legajo dentro del período). Pensado para orígenes externos —hoy ETEC— que calculan sus
 * propios importes y necesitan regenerar sin tocar los cargos de los demás orígenes.
 */
public interface ReplaceCargosByCategoriaUseCase {

    List<CargoLiquidacion> replaceByPeriodo(Integer anho, Integer mes, List<Integer> categoriaIds,
            List<CargoLiquidacion> cargos);

    List<CargoLiquidacion> replaceByLegajo(Long legajoId, Integer anho, Integer mes, List<Integer> categoriaIds,
            List<CargoLiquidacion> cargos);
}
