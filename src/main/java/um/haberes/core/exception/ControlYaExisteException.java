package um.haberes.core.exception;

/**
 * El período de destino ya tiene un registro de control cargado.
 */
public class ControlYaExisteException extends RuntimeException {

    public ControlYaExisteException(Integer anho, Integer mes) {
        super("Ya existen datos de control para " + anho + "/" + mes);
    }

}
