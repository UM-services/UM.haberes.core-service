package um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;

public interface CreateItemUseCase {

    Item createItem(Item item);
}
