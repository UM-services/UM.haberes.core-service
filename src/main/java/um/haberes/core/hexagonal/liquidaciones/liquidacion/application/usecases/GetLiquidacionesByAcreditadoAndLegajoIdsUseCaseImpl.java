package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByAcreditadoAndLegajoIdsUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetLiquidacionesByAcreditadoAndLegajoIdsUseCaseImpl implements GetLiquidacionesByAcreditadoAndLegajoIdsUseCase {

    private final LiquidacionRepository liquidacionRepository;

    @Override
    public List<Liquidacion> getLiquidacionesByAcreditadoAndLegajoIds(Integer anho, Integer mes, List<Long> legajoIds) {
        return liquidacionRepository.findAllByAnhoAndMesAndFechaAcreditacionNotNullAndLegajoIdIn(anho, mes, legajoIds);
    }
}
