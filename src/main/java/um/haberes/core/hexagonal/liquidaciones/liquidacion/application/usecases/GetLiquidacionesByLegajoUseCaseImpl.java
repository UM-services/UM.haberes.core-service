package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionesByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetLiquidacionesByLegajoUseCaseImpl implements GetLiquidacionesByLegajoUseCase {

    private final LiquidacionRepository liquidacionRepository;

    @Override
    public List<Liquidacion> getLiquidacionesByLegajo(Long legajoId) {
        return liquidacionRepository.findAllByLegajoId(legajoId);
    }
}
