package um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.ports.in;

import java.time.OffsetDateTime;
import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;

public interface AcreditarLiquidacionUseCase {

    Optional<Liquidacion> acreditarLiquidacion(Liquidacion liquidacion, OffsetDateTime fechaAcreditacion);
}
