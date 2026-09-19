package um.haberes.core.hexagonal.liquidaciones.item.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.GetItemsByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;

@Component
@RequiredArgsConstructor
public class GetItemsByLegajoUseCaseImpl implements GetItemsByLegajoUseCase {

    private final ItemRepository itemRepository;

    @Override
    public List<Item> getItemsByLegajo(Long legajoId, Integer anho, Integer mes) {
        return itemRepository.findAllByLegajoIdAndAnhoAndMes(legajoId, anho, mes);
    }
}
