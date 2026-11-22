package um.haberes.core.hexagonal.etec.presentismo_horas.application.exception;

/**
 * Conflicto de estado: período acreditado o pérdida por horas ya registrada.
 */
public class EtecPresentismoHorasConflictoException extends EtecPresentismoHorasException {

    private static final long serialVersionUID = 4615877210936417937L;

    public EtecPresentismoHorasConflictoException(String message) {
        super(message);
    }
}
