package um.haberes.core.hexagonal.liquidaciones.item.domain.ports.in;

public interface DeleteZeroItemsByLegajoUseCase {

    void deleteZeroItemsByLegajo(Long legajoId, Integer anho, Integer mes);
}
