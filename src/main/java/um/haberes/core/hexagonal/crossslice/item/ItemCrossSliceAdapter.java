package um.haberes.core.hexagonal.crossslice.item;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.crossslice.item.model.ItemView;
import um.haberes.core.hexagonal.liquidaciones.item.application.service.ItemService;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;

/** Adaptador cross-slice de ítems de liquidación. */
@Component
@RequiredArgsConstructor
public class ItemCrossSliceAdapter implements ItemCrossSlice {

    private final ItemService itemService;

    @Override
    public List<ItemView> porLegajo(Long legajoId, Integer anho, Integer mes) {
        return itemService.getItemsByLegajo(legajoId, anho, mes).stream()
                .map(this::toView)
                .toList();
    }

    private ItemView toView(Item item) {
        String codigoNombre = item.getCodigoNombre();
        if ((codigoNombre == null || codigoNombre.isBlank()) && item.getCodigo() != null) {
            codigoNombre = item.getCodigo().getNombre();
        }
        return new ItemView(item.getItemId(), item.getCodigoId(), codigoNombre, item.getImporte());
    }
}
