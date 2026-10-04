package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByPeriodoLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetLiquidacionesByPeriodoLegajoUseCaseImpl implements GetLiquidacionesByPeriodoLegajoUseCase {

    private final LiquidacionRepository liquidacionRepository;

    @Override
    public List<Liquidacion> getLiquidacionesByPeriodoLegajo(Integer anho, Integer mes, Long legajoId, Integer limit) {
        return liquidacionRepository.findAllByAnhoAndMesAndLegajoId(anho, mes, legajoId, limit);
    }
}
