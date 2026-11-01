package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.DeleteLiquidacionesByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class DeleteLiquidacionesByPeriodoUseCaseImpl implements DeleteLiquidacionesByPeriodoUseCase {

    private final LiquidacionRepository liquidacionRepository;

    @Transactional
    @Override
    public void deleteLiquidacionesByPeriodo(Integer anho, Integer mes) {
        liquidacionRepository.deleteAllByAnhoAndMes(anho, mes);
    }
}
