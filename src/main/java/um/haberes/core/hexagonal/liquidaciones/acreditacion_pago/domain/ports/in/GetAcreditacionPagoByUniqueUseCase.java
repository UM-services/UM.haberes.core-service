package um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.ports.in;

import java.time.OffsetDateTime;
import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.model.AcreditacionPago;

public interface GetAcreditacionPagoByUniqueUseCase {

    Optional<AcreditacionPago> getAcreditacionPagoByUnique(Integer anho, Integer mes, OffsetDateTime fechaPago);
}
