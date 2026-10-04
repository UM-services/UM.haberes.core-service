package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.DeleteCargosNotInByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;

@Component
@RequiredArgsConstructor
public class DeleteCargosNotInByPeriodoUseCaseImpl implements DeleteCargosNotInByPeriodoUseCase {

    private final CargoLiquidacionRepository cargoLiquidacionRepository;

    @Transactional
    @Override
    public void deleteCargosNotInByPeriodo(List<Long> legajoIds, Integer anho, Integer mes) {
        cargoLiquidacionRepository.deleteAllByLegajoIdNotInAndAnhoAndMes(legajoIds, anho, mes);
    }
}
