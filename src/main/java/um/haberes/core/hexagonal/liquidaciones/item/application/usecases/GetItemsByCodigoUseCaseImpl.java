package um.haberes.core.hexagonal.liquidaciones.item.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.GetItemsByCodigoUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;

@Component
@RequiredArgsConstructor
public class GetItemsByCodigoUseCaseImpl implements GetItemsByCodigoUseCase {

    private final ItemRepository itemRepository;

    @Override
    public List<Item> getItemsByCodigo(Integer codigoId, Integer anho, Integer mes) {
        return itemRepository.findAllByCodigoIdAndAnhoAndMes(codigoId, anho, mes);
    }
}
