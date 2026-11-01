package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargoLiquidacionByIdUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetCargoLiquidacionByIdUseCaseImpl implements GetCargoLiquidacionByIdUseCase {

    private final CargoLiquidacionRepository cargoLiquidacionRepository;

    @Override
    public Optional<CargoLiquidacion> getCargoLiquidacionById(Long cargoLiquidacionId) {
        return cargoLiquidacionRepository.findByCargoLiquidacionId(cargoLiquidacionId);
    }
}
