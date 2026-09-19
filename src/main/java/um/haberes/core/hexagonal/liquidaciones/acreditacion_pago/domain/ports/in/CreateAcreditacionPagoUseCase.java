package um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.model.AcreditacionPago;

public interface CreateAcreditacionPagoUseCase {

    AcreditacionPago createAcreditacionPago(AcreditacionPago acreditacionPago);
}
