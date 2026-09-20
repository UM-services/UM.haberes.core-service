package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;

public interface GetCargosActivosByPeriodoUseCase {

    List<CargoLiquidacion> getCargosActivosByPeriodo(Integer anho, Integer mes);
}
