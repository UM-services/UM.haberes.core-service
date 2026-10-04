package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.ItemQueryPort;
import um.haberes.core.hexagonal.liquidaciones.item.application.service.ItemService;

@Component
@RequiredArgsConstructor
public class ItemServiceQueryAdapter implements ItemQueryPort {

    private static final int EXISTS_LIMIT = 1;

    private final ItemService itemService;

    @Override
    public boolean existsByLegajoIdAndAnhoAndMes(Long legajoId, Integer anho, Integer mes) {
        return !itemService.getItemsByPeriodoAndLegajo(anho, mes, legajoId, EXISTS_LIMIT).isEmpty();
    }
}
