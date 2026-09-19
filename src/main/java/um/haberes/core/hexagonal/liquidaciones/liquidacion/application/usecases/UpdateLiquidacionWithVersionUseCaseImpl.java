package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.UpdateLiquidacionUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.UpdateLiquidacionWithVersionUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionVersionRepository;

@Component
@RequiredArgsConstructor
public class UpdateLiquidacionWithVersionUseCaseImpl implements UpdateLiquidacionWithVersionUseCase {

    private final LiquidacionVersionRepository liquidacionVersionRepository;
    private final UpdateLiquidacionUseCase updateLiquidacionUseCase;

    @Transactional
    @Override
    public Optional<Liquidacion> updateLiquidacionWithVersion(Long liquidacionId, Liquidacion liquidacion, Integer version) {
        Optional<Liquidacion> updated = updateLiquidacionUseCase.updateLiquidacion(liquidacionId, liquidacion);
        updated.ifPresent(saved -> liquidacionVersionRepository.save(
                CreateLiquidacionWithVersionUseCaseImpl.toVersion(saved, version)));
        return updated;
    }
}
