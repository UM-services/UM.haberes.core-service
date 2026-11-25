package um.haberes.core.hexagonal.seguridad.application.exception;

public class SeguridadException extends RuntimeException {

    public SeguridadException(String message) {
        super(message);
    }
}
