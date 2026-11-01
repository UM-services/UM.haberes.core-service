package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargosByLegajosAndCategoriasAndPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetCargosByLegajosAndCategoriasAndPeriodoUseCaseImpl
        implements GetCargosByLegajosAndCategoriasAndPeriodoUseCase {

    private final CargoLiquidacionRepository cargoLiquidacionRepository;

    @Override
    public List<CargoLiquidacion> getCargosByLegajosAndCategoriasAndPeriodo(List<Long> legajoIds,
            List<Integer> categoriaIds, Integer anho, Integer mes) {
        return cargoLiquidacionRepository.findAllByLegajoIdInAndCategoriaIdInAndAnhoAndMes(legajoIds, categoriaIds,
                anho, mes);
    }
}
