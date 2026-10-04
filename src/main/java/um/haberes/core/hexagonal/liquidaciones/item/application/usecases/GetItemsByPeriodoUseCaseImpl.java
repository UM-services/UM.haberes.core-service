package um.haberes.core.hexagonal.liquidaciones.item.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.GetItemsByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;

@Component
@RequiredArgsConstructor
public class GetItemsByPeriodoUseCaseImpl implements GetItemsByPeriodoUseCase {

    private final ItemRepository itemRepository;

    @Override
    public List<Item> getItemsByPeriodo(Integer anho, Integer mes, Integer limit) {
        return itemRepository.findAllByAnhoAndMes(anho, mes, limit);
    }
}
