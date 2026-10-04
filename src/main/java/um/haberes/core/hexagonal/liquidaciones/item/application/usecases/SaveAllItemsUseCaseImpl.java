package um.haberes.core.hexagonal.liquidaciones.item.application.usecases;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.ItemVersion;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.SaveAllItemsUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemVersionRepository;

@Component
@RequiredArgsConstructor
public class SaveAllItemsUseCaseImpl implements SaveAllItemsUseCase {

    private final ItemRepository itemRepository;
    private final ItemVersionRepository itemVersionRepository;

    @Transactional
    @Override
    public List<Item> saveAllItems(List<Item> items) {
        List<ItemVersion> itemVersions = items.stream()
                .map(this::toVersion)
                .collect(Collectors.toList());
        List<Item> saved = itemRepository.saveAll(items);
        itemVersionRepository.saveAll(itemVersions);
        return saved;
    }

    private ItemVersion toVersion(Item item) {
        ItemVersion.ItemVersionBuilder builder = ItemVersion.builder()
                .legajoId(item.getLegajoId())
                .anho(item.getAnho())
                .mes(item.getMes())
                .codigoId(item.getCodigoId());
        if (item.getCodigoNombre() != null) {
            builder.codigoNombre(item.getCodigoNombre());
        }
        if (item.getImporte() != null) {
            builder.importe(item.getImporte());
        }
        return builder.build();
    }
}
