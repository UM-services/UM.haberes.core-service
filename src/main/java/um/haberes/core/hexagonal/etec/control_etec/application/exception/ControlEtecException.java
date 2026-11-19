package um.haberes.core.hexagonal.etec.control_etec.application.exception;

/** Error de validación de los parámetros del control ETEC. */
public class ControlEtecException extends RuntimeException {

    public ControlEtecException(String message) {
        super(message);
    }
}
