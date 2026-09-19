package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;

public interface GetCargoLiquidacionByIdUseCase {

    Optional<CargoLiquidacion> getCargoLiquidacionById(Long cargoLiquidacionId);
}
