package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;

public interface GetCargosByLegajosAndCategoriasAndPeriodoUseCase {

    List<CargoLiquidacion> getCargosByLegajosAndCategoriasAndPeriodo(List<Long> legajoIds, List<Integer> categoriaIds,
            Integer anho, Integer mes);
}
