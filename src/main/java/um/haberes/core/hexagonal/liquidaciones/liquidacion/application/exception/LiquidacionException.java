package um.haberes.core.hexagonal.liquidaciones.liquidacion.application.exception;

public class LiquidacionException extends RuntimeException {

    private static final long serialVersionUID = -8452069536111975095L;

    public LiquidacionException() {
        super("Could not find Liquidacion");
    }

    public LiquidacionException(Long liquidacionId) {
        super("Could not find Liquidacion with id: " + liquidacionId);
    }

    public LiquidacionException(Long legajoId, Integer anho, Integer mes) {
        super("Could not find Liquidacion for legajo " + legajoId + "/" + anho + "/" + mes);
    }
}
