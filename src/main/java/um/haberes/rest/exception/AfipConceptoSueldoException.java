package um.haberes.rest.exception;

public class AfipConceptoSueldoException extends RuntimeException {
    public AfipConceptoSueldoException(Long afipConceptoSueldoId) {
        super("Cannot find AfipConceptoSueldo -> " + afipConceptoSueldoId);
    }

}
