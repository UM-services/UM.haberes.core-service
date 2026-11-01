package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.CreateLiquidacionUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class CreateLiquidacionUseCaseImpl implements CreateLiquidacionUseCase {

    private final LiquidacionRepository liquidacionRepository;

    @Transactional
    @Override
    public Liquidacion createLiquidacion(Liquidacion liquidacion) {
        return liquidacionRepository.save(liquidacion);
    }
}
