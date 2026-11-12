package um.haberes.core.hexagonal.etec.asignacion_cargo.application.exception;

public class EtecAsignacionCargoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EtecAsignacionCargoException(Long asignacionCargoId) {
        super("Cannot find EtecAsignacionCargo " + asignacionCargoId);
    }

    public EtecAsignacionCargoException(String message) {
        super(message);
    }
}
