package um.haberes.core.hexagonal.liquidaciones.sijp.application.exception;

/** El período no tiene control cargado, por lo que no se pueden calcular los topes SIJP. */
public class SijpSinControlException extends SijpException {

    public SijpSinControlException(Integer anho, Integer mes) {
        super("No existe el control del período " + anho + "/" + mes);
    }

}
