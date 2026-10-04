package um.haberes.core.hexagonal.liquidaciones.item.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.ItemVersion;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.CreateItemUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemRepository;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.out.ItemVersionRepository;

@Component
@RequiredArgsConstructor
public class CreateItemUseCaseImpl implements CreateItemUseCase {

    private final ItemRepository itemRepository;
    private final ItemVersionRepository itemVersionRepository;

    @Transactional
    @Override
    public Item createItem(Item item) {
        Item saved = itemRepository.save(item);
        itemVersionRepository.save(toVersion(saved));
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
