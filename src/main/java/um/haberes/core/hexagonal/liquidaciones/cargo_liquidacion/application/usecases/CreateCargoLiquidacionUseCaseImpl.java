package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.CreateCargoLiquidacionUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;

@Component
@RequiredArgsConstructor
public class CreateCargoLiquidacionUseCaseImpl implements CreateCargoLiquidacionUseCase {

    private final CargoLiquidacionRepository cargoLiquidacionRepository;

    @Override
    public CargoLiquidacion createCargoLiquidacion(CargoLiquidacion cargoLiquidacion) {
        return cargoLiquidacionRepository.save(cargoLiquidacion);
    }
}
