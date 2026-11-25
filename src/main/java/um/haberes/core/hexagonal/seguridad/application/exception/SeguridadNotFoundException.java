package um.haberes.core.hexagonal.seguridad.application.exception;

public class SeguridadNotFoundException extends RuntimeException {

    public SeguridadNotFoundException(String message) {
        super(message);
    }
}
