package um.haberes.core.hexagonal.etec.presentismo.application.exception;

/**
 * Conflicto de estado: período acreditado o pérdida de presentismo ya registrada.
 */
public class EtecPresentismoConflictoException extends EtecPresentismoException {

    private static final long serialVersionUID = -5120873164091734212L;

    public EtecPresentismoConflictoException(String message) {
        super(message);
    }
}
