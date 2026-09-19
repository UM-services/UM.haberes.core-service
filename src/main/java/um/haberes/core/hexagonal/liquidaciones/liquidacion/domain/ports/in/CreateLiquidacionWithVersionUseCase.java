package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;

public interface CreateLiquidacionWithVersionUseCase {

    Liquidacion createLiquidacionWithVersion(Liquidacion liquidacion, Integer version);
}
