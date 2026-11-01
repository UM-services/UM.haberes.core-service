package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;

public interface GetCargosByLegajoExcludingCategoriasAndPeriodoUseCase {

    List<CargoLiquidacion> getCargosByLegajoExcludingCategoriasAndPeriodo(Long legajoId, Integer anho, Integer mes,
            List<Integer> categoriaIds);
}
