package um.haberes.core.hexagonal.liquidaciones.item.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.GetItemsByPeriodoAndLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;

@Component
@RequiredArgsConstructor
public class GetItemsByPeriodoAndLegajoUseCaseImpl implements GetItemsByPeriodoAndLegajoUseCase {

    private final ItemRepository itemRepository;

    @Override
    public List<Item> getItemsByPeriodoAndLegajo(Integer anho, Integer mes, Long legajoId, Integer limit) {
        return itemRepository.findAllByAnhoAndMesAndLegajoId(anho, mes, legajoId, limit);
    }
}
