package um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.item.domain.model.ItemVersion;

public interface ItemVersionRepository {

    ItemVersion save(ItemVersion itemVersion);

    List<ItemVersion> saveAll(List<ItemVersion> itemVersions);
}
