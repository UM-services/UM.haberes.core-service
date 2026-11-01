package um.haberes.core.hexagonal.liquidaciones.item.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.GetItemByUniqueKeyUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;

@Component
@RequiredArgsConstructor
public class GetItemByUniqueKeyUseCaseImpl implements GetItemByUniqueKeyUseCase {

    private final ItemRepository itemRepository;

    @Override
    public Optional<Item> getItemByUniqueKey(Long legajoId, Integer anho, Integer mes, Integer codigoId) {
        return itemRepository.findByLegajoIdAndAnhoAndMesAndCodigoId(legajoId, anho, mes, codigoId);
    }
}
