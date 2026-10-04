package um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;

public interface GetItemByUniqueKeyUseCase {

    Optional<Item> getItemByUniqueKey(Long legajoId, Integer anho, Integer mes, Integer codigoId);
}
