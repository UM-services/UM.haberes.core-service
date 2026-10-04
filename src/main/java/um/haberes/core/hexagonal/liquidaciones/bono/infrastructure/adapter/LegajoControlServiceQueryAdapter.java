package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.exception.LegajoControlException;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.LegajoControlQueryPort;
import um.haberes.core.service.LegajoControlService;

@Component
@RequiredArgsConstructor
public class LegajoControlServiceQueryAdapter implements LegajoControlQueryPort {

    private final LegajoControlService legajoControlService;

    @Override
    public boolean existsByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        try {
            legajoControlService.findByUnique(legajoId, anho, mes);
            return true;
        } catch (LegajoControlException e) {
            return false;
        }
    }
}
