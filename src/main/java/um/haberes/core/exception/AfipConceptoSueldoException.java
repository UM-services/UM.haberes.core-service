package um.haberes.core.exception;

public class AfipConceptoSueldoException extends RuntimeException {
    public AfipConceptoSueldoException(Long afipConceptoSueldoId) {
        super("Cannot find AfipConceptoSueldo -> " + afipConceptoSueldoId);
    }

}
