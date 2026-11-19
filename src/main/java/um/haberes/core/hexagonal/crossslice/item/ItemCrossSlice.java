package um.haberes.core.hexagonal.crossslice.item;

import java.util.List;

import um.haberes.core.hexagonal.crossslice.item.model.ItemView;

/** Contrato de cross-slice para los ítems calculados de un legajo y período. */
public interface ItemCrossSlice {

    List<ItemView> porLegajo(Long legajoId, Integer anho, Integer mes);
}
