package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.adapter;

import java.util.List;

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

    @Override
    public List<Long> findLegajoIdsByDependencia(Integer dependenciaId, Integer anho, Integer mes,
                                                 String salida) {
        return liquidacionService.getLiquidacionesByDependencia(dependenciaId, anho, mes, salida).stream()
                .map(um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion::getLegajoId)
                .distinct()
                .toList();
    }
}
