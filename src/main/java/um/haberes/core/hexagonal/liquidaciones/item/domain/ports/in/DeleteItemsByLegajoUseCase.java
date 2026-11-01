package um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in;

public interface DeleteItemsByLegajoUseCase {

    void deleteItemsByLegajo(Long legajoId, Integer anho, Integer mes);
}
