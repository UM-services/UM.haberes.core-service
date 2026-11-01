package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByPeriodoAndLegajoIdsUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetLiquidacionesByPeriodoAndLegajoIdsUseCaseImpl implements GetLiquidacionesByPeriodoAndLegajoIdsUseCase {

    private final LiquidacionRepository liquidacionRepository;

    @Override
    public List<Liquidacion> getLiquidacionesByPeriodoAndLegajoIds(Integer anho, Integer mes, List<Long> legajoIds) {
        return liquidacionRepository.findAllByAnhoAndMesAndLegajoIdIn(anho, mes, legajoIds);
    }
}
