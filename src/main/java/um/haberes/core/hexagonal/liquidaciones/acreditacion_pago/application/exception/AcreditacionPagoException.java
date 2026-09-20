package um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.application.exception;

import java.text.MessageFormat;
import java.time.OffsetDateTime;

public class AcreditacionPagoException extends RuntimeException {

    private static final long serialVersionUID = -6896761854027000589L;

    public AcreditacionPagoException() {
        super("Could not find AcreditacionPago");
    }

    public AcreditacionPagoException(Integer anho, Integer mes, OffsetDateTime fechaPago) {
        super(MessageFormat.format("Cannot find AcreditacionPago {0}/{1}/{2}", anho, mes, fechaPago));
    }

    public AcreditacionPagoException(Long acreditacionPagoId) {
        super("Could not find AcreditacionPago " + acreditacionPagoId);
    }
}
