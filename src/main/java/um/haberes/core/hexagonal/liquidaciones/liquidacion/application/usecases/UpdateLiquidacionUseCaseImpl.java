package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.UpdateLiquidacionUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class UpdateLiquidacionUseCaseImpl implements UpdateLiquidacionUseCase {

    private final LiquidacionRepository liquidacionRepository;

    @Transactional
    @Override
    public Optional<Liquidacion> updateLiquidacion(Long liquidacionId, Liquidacion liquidacion) {
        if (liquidacionRepository.findByLiquidacionId(liquidacionId).isEmpty()) {
            return Optional.empty();
        }
        liquidacion.setLiquidacionId(liquidacionId);
        return Optional.of(liquidacionRepository.save(liquidacion));
    }
}
