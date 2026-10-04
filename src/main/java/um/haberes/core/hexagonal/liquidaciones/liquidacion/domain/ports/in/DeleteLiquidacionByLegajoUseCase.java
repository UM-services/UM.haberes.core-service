package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in;

public interface DeleteLiquidacionByLegajoUseCase {

    void deleteLiquidacionByLegajo(Long legajoId, Integer anho, Integer mes);
}
