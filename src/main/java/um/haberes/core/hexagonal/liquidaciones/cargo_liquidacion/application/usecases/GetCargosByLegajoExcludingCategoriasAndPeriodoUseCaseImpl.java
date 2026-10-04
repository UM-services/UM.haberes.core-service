package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargosByLegajoExcludingCategoriasAndPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetCargosByLegajoExcludingCategoriasAndPeriodoUseCaseImpl
        implements GetCargosByLegajoExcludingCategoriasAndPeriodoUseCase {

    private final CargoLiquidacionRepository cargoLiquidacionRepository;

    @Override
    public List<CargoLiquidacion> getCargosByLegajoExcludingCategoriasAndPeriodo(Long legajoId, Integer anho,
            Integer mes, List<Integer> categoriaIds) {
        return cargoLiquidacionRepository.findAllByLegajoIdAndAnhoAndMesAndCategoriaIdNotIn(legajoId, anho, mes,
                categoriaIds);
    }
}
