package um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;

public interface GetItemsByPeriodoUseCase {

    List<Item> getItemsByPeriodo(Integer anho, Integer mes, Integer limit);
}
