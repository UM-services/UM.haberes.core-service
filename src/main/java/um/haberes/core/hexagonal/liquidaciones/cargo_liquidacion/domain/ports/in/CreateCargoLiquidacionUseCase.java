package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;

public interface CreateCargoLiquidacionUseCase {

    CargoLiquidacion createCargoLiquidacion(CargoLiquidacion cargoLiquidacion);
}
