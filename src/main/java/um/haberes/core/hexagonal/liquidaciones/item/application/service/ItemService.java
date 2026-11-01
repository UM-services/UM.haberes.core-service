package um.haberes.core.hexagonal.liquidaciones.item.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.application.exception.ItemException;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.CheckItemsOnlyEtecUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.CreateItemUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.DeleteItemsByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.DeleteItemsByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.DeleteZeroItemsByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.GetItemByUniqueKeyUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.GetItemsByCodigoUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.GetItemsByLegajoAndCodigosUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.GetItemsByLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.GetItemsByNetoPositivoUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.GetItemsByPeriodoAndLegajoUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.GetItemsByPeriodoUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.SaveAllItemsUseCase;
import um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in.UpdateItemUseCase;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final GetItemsByCodigoUseCase getItemsByCodigoUseCase;
    private final GetItemsByLegajoUseCase getItemsByLegajoUseCase;
    private final GetItemsByPeriodoUseCase getItemsByPeriodoUseCase;
    private final GetItemsByPeriodoAndLegajoUseCase getItemsByPeriodoAndLegajoUseCase;
    private final GetItemsByNetoPositivoUseCase getItemsByNetoPositivoUseCase;
    private final GetItemsByLegajoAndCodigosUseCase getItemsByLegajoAndCodigosUseCase;
    private final GetItemByUniqueKeyUseCase getItemByUniqueKeyUseCase;
    private final CreateItemUseCase createItemUseCase;
    private final UpdateItemUseCase updateItemUseCase;
    private final SaveAllItemsUseCase saveAllItemsUseCase;
    private final DeleteItemsByPeriodoUseCase deleteItemsByPeriodoUseCase;
    private final DeleteItemsByLegajoUseCase deleteItemsByLegajoUseCase;
    private final DeleteZeroItemsByLegajoUseCase deleteZeroItemsByLegajoUseCase;
    private final CheckItemsOnlyEtecUseCase checkItemsOnlyEtecUseCase;

    public List<Item> getItemsByCodigo(Integer codigoId, Integer anho, Integer mes) {
        return getItemsByCodigoUseCase.getItemsByCodigo(codigoId, anho, mes);
    }

    public List<Item> getItemsByLegajo(Long legajoId, Integer anho, Integer mes) {
        return getItemsByLegajoUseCase.getItemsByLegajo(legajoId, anho, mes);
    }

    public List<Item> getItemsByPeriodo(Integer anho, Integer mes, Integer limit) {
        return getItemsByPeriodoUseCase.getItemsByPeriodo(anho, mes, limit);
    }

    public List<Item> getItemsByPeriodoAndLegajo(Integer anho, Integer mes, Long legajoId, Integer limit) {
        return getItemsByPeriodoAndLegajoUseCase.getItemsByPeriodoAndLegajo(anho, mes, legajoId, limit);
    }

    public List<Item> getItemsByNetoPositivo(Integer anho, Integer mes) {
        return getItemsByNetoPositivoUseCase.getItemsByNetoPositivo(anho, mes);
    }

    public List<Item> getItemsByLegajoAndCodigos(Long legajoId, Integer anho, Integer mes, List<Integer> codigoIds) {
        return getItemsByLegajoAndCodigosUseCase.getItemsByLegajoAndCodigos(legajoId, anho, mes, codigoIds);
    }

    public Item findByUnique(Long legajoId, Integer anho, Integer mes, Integer codigoId) {
        return getItemByUniqueKeyUseCase.getItemByUniqueKey(legajoId, anho, mes, codigoId)
                .orElseThrow(() -> new ItemException(legajoId, anho, mes, codigoId));
    }

    public Item add(Item item) {
        return createItemUseCase.createItem(item);
    }

    public Item update(Item item, Long itemId) {
        return updateItemUseCase.updateItem(itemId, item)
                .orElseThrow(() -> new ItemException(itemId));
    }

    public List<Item> saveAllItems(List<Item> items) {
        return saveAllItemsUseCase.saveAllItems(items);
    }

    public void deleteByPeriodo(Integer anho, Integer mes) {
        deleteItemsByPeriodoUseCase.deleteItemsByPeriodo(anho, mes);
    }

    public void deleteAllByLegajo(Long legajoId, Integer anho, Integer mes) {
        deleteItemsByLegajoUseCase.deleteItemsByLegajo(legajoId, anho, mes);
    }

    public void deleteAllByZero(Long legajoId, Integer anho, Integer mes) {
        deleteZeroItemsByLegajoUseCase.deleteZeroItemsByLegajo(legajoId, anho, mes);
    }

    public boolean onlyETEC(Long legajoId, Integer anho, Integer mes) {
        return checkItemsOnlyEtecUseCase.checkItemsOnlyEtec(legajoId, anho, mes);
    }
}
