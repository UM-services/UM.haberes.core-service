package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionByUniqueKeyUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetLiquidacionByUniqueKeyUseCaseImpl implements GetLiquidacionByUniqueKeyUseCase {

    private final LiquidacionRepository liquidacionRepository;

    @Override
    public Optional<Liquidacion> getLiquidacionByUniqueKey(Long legajoId, Integer anho, Integer mes) {
        return liquidacionRepository.findByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }
}
