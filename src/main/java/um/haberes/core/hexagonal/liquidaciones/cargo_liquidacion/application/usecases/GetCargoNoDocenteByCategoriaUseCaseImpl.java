package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetCargoNoDocenteByCategoriaUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetCargoNoDocenteByCategoriaUseCaseImpl implements GetCargoNoDocenteByCategoriaUseCase {

    private final CargoLiquidacionRepository cargoLiquidacionRepository;

    @Override
    public Optional<CargoLiquidacion> getCargoNoDocenteByCategoria(Long legajoId, Integer anho, Integer mes,
            Integer categoriaId) {
        return cargoLiquidacionRepository.findByLegajoIdAndAnhoAndMesAndCategoriaId(legajoId, anho, mes, categoriaId);
    }
}
