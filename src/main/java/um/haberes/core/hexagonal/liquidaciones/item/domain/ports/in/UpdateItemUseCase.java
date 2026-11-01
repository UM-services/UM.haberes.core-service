package um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;

public interface UpdateItemUseCase {

    Optional<Item> updateItem(Long itemId, Item item);
}
