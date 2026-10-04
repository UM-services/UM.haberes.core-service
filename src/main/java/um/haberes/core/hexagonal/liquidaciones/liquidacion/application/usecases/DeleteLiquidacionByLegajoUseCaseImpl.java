package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.DeleteLiquidacionByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class DeleteLiquidacionByLegajoUseCaseImpl implements DeleteLiquidacionByLegajoUseCase {

    private final LiquidacionRepository liquidacionRepository;

    @Transactional
    @Override
    public void deleteLiquidacionByLegajo(Long legajoId, Integer anho, Integer mes) {
        liquidacionRepository.deleteByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }
}
