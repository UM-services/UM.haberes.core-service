package um.haberes.core.exception.view;

public class TotalItemException extends RuntimeException {
    public TotalItemException(Integer anho, Integer mes, Integer codigoId) {

        super("Cannot found TotalItem " + anho + "/" + mes + "/" + codigoId);
    }
}
