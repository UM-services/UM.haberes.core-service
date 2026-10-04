package um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in;

public interface CheckItemsOnlyEtecUseCase {

    boolean checkItemsOnlyEtec(Long legajoId, Integer anho, Integer mes);
}
