package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.GetLiquidacionByIdUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class GetLiquidacionByIdUseCaseImpl implements GetLiquidacionByIdUseCase {

    private final LiquidacionRepository liquidacionRepository;

    @Override
    public Optional<Liquidacion> getLiquidacionById(Long liquidacionId) {
        return liquidacionRepository.findByLiquidacionId(liquidacionId);
    }
}
