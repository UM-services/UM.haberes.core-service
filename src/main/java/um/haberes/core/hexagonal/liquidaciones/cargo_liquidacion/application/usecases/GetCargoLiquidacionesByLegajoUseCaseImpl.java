package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargoLiquidacionesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetCargoLiquidacionesByLegajoUseCaseImpl implements GetCargoLiquidacionesByLegajoUseCase {

    private final CargoLiquidacionRepository cargoLiquidacionRepository;

    @Override
    public List<CargoLiquidacion> getCargosByLegajo(Long legajoId, Integer anho, Integer mes) {
        return cargoLiquidacionRepository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }
}
