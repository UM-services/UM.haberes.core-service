package um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.ports.out;

import java.time.OffsetDateTime;
import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.model.AcreditacionPago;

public interface AcreditacionPagoRepository {

    Optional<AcreditacionPago> findByAnhoAndMesAndFechaPago(Integer anho, Integer mes, OffsetDateTime fechaPago);

    AcreditacionPago create(AcreditacionPago acreditacionPago);

    Optional<AcreditacionPago> update(Long acreditacionPagoId, AcreditacionPago acreditacionPago);
}
