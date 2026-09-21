package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.LiquidacionQueryPort;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.exception.LiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.service.LiquidacionService;

@Component
@RequiredArgsConstructor
public class LiquidacionServiceQueryAdapter implements LiquidacionQueryPort {

    private final LiquidacionService liquidacionService;

    @Override
    public boolean existsByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        try {
            liquidacionService.getLiquidacionByUniqueKey(legajoId, anho, mes);
            return true;
        } catch (LiquidacionException e) {
            return false;
        }
    }
}
