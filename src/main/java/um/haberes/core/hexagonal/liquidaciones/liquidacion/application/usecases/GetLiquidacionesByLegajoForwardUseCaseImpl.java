package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.LiquidacionPeriodoForward;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByLegajoForwardUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionPeriodoRepository;

@Component
@RequiredArgsConstructor
public class GetLiquidacionesByLegajoForwardUseCaseImpl implements GetLiquidacionesByLegajoForwardUseCase {

    private final LiquidacionPeriodoRepository liquidacionPeriodoRepository;

    @Override
    public List<LiquidacionPeriodoForward> getLiquidacionesByLegajoForward(Long legajoId, Integer anho, Integer mes) {
        return liquidacionPeriodoRepository.findAllByLegajoIdForward(legajoId, anho, mes);
    }
}
