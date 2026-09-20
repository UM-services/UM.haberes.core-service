package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargosActivosByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetCargosActivosByPeriodoUseCaseImpl implements GetCargosActivosByPeriodoUseCase {

    private static final String SITUACION_ACTIVA = "A";

    private final CargoLiquidacionRepository cargoLiquidacionRepository;

    @Override
    public List<CargoLiquidacion> getCargosActivosByPeriodo(Integer anho, Integer mes) {
        return cargoLiquidacionRepository.findAllByAnhoAndMesAndSituacion(anho, mes, SITUACION_ACTIVA);
    }
}
