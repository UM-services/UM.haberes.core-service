package um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.application.exception;

public class LegajoCodigoImputacionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LegajoCodigoImputacionException() {
        super("No se pudo encontrar el LegajoCodigoImputacion");
    }

    public LegajoCodigoImputacionException(Long legajoCodigoImputacionId) {
        super("No se pudo encontrar el LegajoCodigoImputacion con id: " + legajoCodigoImputacionId);
    }
}
