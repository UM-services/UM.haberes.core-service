package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.usecases;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in.AcreditarLiquidacionUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.out.LiquidacionRepository;

@Component
@RequiredArgsConstructor
public class AcreditarLiquidacionUseCaseImpl implements AcreditarLiquidacionUseCase {

    private static final byte BLOQUEADO_SI = (byte) 1;

    private final LiquidacionRepository liquidacionRepository;

    @Transactional
    @Override
    public Optional<Liquidacion> acreditarLiquidacion(Liquidacion liquidacion, OffsetDateTime fechaAcreditacion) {
        if (liquidacion == null || liquidacion.getLiquidacionId() == null
                || liquidacionRepository.findByLiquidacionId(liquidacion.getLiquidacionId()).isEmpty()) {
            return Optional.empty();
        }
        liquidacion.setFechaAcreditacion(fechaAcreditacion);
        liquidacion.setBloqueado(BLOQUEADO_SI);
        return Optional.of(liquidacionRepository.save(liquidacion));
    }
}
