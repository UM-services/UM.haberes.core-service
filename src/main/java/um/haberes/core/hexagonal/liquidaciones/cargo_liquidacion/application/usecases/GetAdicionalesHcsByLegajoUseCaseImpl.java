package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.GetAdicionalesHcsByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetAdicionalesHcsByLegajoUseCaseImpl implements GetAdicionalesHcsByLegajoUseCase {

    private static final int ADICIONAL_HCS_CATEGORIA_ID_DESDE = 801;
    private static final int ADICIONAL_HCS_CATEGORIA_ID_HASTA = 811;

    private final CargoLiquidacionRepository cargoLiquidacionRepository;

    @Override
    public List<CargoLiquidacion> getAdicionalesHcsByLegajo(Long legajoId, Integer anho, Integer mes) {
        return cargoLiquidacionRepository.findAllByLegajoIdAndAnhoAndMesAndCategoriaIdBetween(legajoId, anho, mes,
                ADICIONAL_HCS_CATEGORIA_ID_DESDE, ADICIONAL_HCS_CATEGORIA_ID_HASTA);
    }
}
