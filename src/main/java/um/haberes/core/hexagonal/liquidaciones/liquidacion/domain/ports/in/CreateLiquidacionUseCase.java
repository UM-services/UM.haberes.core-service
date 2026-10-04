package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;

public interface CreateLiquidacionUseCase {

    Liquidacion createLiquidacion(Liquidacion liquidacion);
}
