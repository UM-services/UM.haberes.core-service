package um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in;

public interface DeleteItemsByPeriodoUseCase {

    void deleteItemsByPeriodo(Integer anho, Integer mes);
}
