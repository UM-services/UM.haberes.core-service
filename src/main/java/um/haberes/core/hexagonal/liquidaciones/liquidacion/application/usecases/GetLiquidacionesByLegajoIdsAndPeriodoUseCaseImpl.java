package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByLegajoIdsAndPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetLiquidacionesByLegajoIdsAndPeriodoUseCaseImpl implements GetLiquidacionesByLegajoIdsAndPeriodoUseCase {

    private final LiquidacionRepository liquidacionRepository;

    @Override
    public List<Liquidacion> getLiquidacionesByLegajoIdsAndPeriodo(List<Long> legajoIds, Integer anho, Integer mes) {
        return liquidacionRepository.findAllByLegajoIdInAndAnhoAndMes(legajoIds, anho, mes);
    }
}
