package um.haberes.core.hexagonal.liquidaciones.codigo.application.exception;

public class CodigoException extends RuntimeException {

    private static final long serialVersionUID = -4891436742167836975L;

    public CodigoException() {
        super("Cannot find Codigo");
    }

    public CodigoException(Integer codigoId) {
        super("Cannot find Codigo " + codigoId);
    }
}
