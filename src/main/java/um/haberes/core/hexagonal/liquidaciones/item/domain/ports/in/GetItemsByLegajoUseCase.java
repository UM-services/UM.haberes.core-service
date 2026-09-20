package um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;

public interface GetItemsByLegajoUseCase {

    List<Item> getItemsByLegajo(Long legajoId, Integer anho, Integer mes);
}
