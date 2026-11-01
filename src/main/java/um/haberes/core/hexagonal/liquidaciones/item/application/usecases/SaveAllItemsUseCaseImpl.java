package um.haberes.core.hexagonal.liquidaciones.item.application.usecases;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.audit.AuditBulkScope;
import um.haberes.core.audit.AuditBulkService;
import um.haberes.core.audit.BulkContext;
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
    private final AuditBulkService auditBulk;

    @Transactional
    @Override
    public List<Item> saveAllItems(List<Item> items) {
        if (items.isEmpty()) {
            return itemRepository.saveAll(items);
        }
        List<ItemVersion> itemVersions = items.stream()
                .map(this::toVersion)
                .collect(Collectors.toList());
        try (AuditBulkScope ignored = auditBulk.begin(context(items))) {
            List<Item> saved = itemRepository.saveAll(items);
            itemVersionRepository.saveAll(itemVersions);
            return saved;
        }
    }

    /**
     * Un guardado masivo puede ser de un legajo (recálculo) o de varios (endpoint). El
     * resumen sólo lleva período/legajo cuando son homogéneos, para no atribuir el evento al
     * legajo equivocado.
     */
    private BulkContext context(List<Item> items) {
        Item first = items.getFirst();
        boolean samePeriod = items.stream()
                .allMatch(item -> Objects.equals(item.getAnho(), first.getAnho())
                        && Objects.equals(item.getMes(), first.getMes()));
        boolean sameLegajo = items.stream()
                .allMatch(item -> Objects.equals(item.getLegajoId(), first.getLegajoId()));
        return BulkContext.of("recalculo-items",
                samePeriod ? first.getAnho() : null,
                samePeriod ? first.getMes() : null,
                sameLegajo ? first.getLegajoId() : null);
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
