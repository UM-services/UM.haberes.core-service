package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in;

public interface DeleteLiquidacionesByPeriodoUseCase {

    void deleteLiquidacionesByPeriodo(Integer anho, Integer mes);
}
