package um.haberes.core.exception;

/**
 * No hay registro de control en el período inmediatamente anterior al solicitado.
 */
public class ControlOrigenException extends RuntimeException {

    public ControlOrigenException(Integer anho, Integer mes) {
        super("No existen datos de control para el período anterior " + anho + "/" + mes);
    }

}
