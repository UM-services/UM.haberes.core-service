package um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;

public interface SaveAllItemsUseCase {

    List<Item> saveAllItems(List<Item> items);
}
