package um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;

public interface GetItemsByPeriodoAndLegajoUseCase {

    List<Item> getItemsByPeriodoAndLegajo(Integer anho, Integer mes, Long legajoId, Integer limit);
}
