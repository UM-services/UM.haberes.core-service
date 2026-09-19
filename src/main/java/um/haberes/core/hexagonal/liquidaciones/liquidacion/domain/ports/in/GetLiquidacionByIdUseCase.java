package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;

public interface GetLiquidacionByIdUseCase {

    Optional<Liquidacion> getLiquidacionById(Long liquidacionId);
}
