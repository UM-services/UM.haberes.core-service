package um.haberes.core.hexagonal.liquidaciones.item.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.GetItemsByLegajoAndCodigosUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;

@Component
@RequiredArgsConstructor
public class GetItemsByLegajoAndCodigosUseCaseImpl implements GetItemsByLegajoAndCodigosUseCase {

    private final ItemRepository itemRepository;

    @Override
    public List<Item> getItemsByLegajoAndCodigos(Long legajoId, Integer anho, Integer mes, List<Integer> codigoIds) {
        return itemRepository.findAllByLegajoIdAndAnhoAndMesAndCodigoIdIn(legajoId, anho, mes, codigoIds);
    }
}
