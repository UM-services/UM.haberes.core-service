package um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.ports.in;

import java.util.Optional;

import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.model.AcreditacionPago;

public interface UpdateAcreditacionPagoUseCase {

    Optional<AcreditacionPago> updateAcreditacionPago(Long acreditacionPagoId, AcreditacionPago acreditacionPago);
}
