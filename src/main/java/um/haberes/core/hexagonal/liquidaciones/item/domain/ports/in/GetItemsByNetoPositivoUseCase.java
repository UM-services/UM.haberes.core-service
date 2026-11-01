package um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;

public interface GetItemsByNetoPositivoUseCase {

    List<Item> getItemsByNetoPositivo(Integer anho, Integer mes);
}
