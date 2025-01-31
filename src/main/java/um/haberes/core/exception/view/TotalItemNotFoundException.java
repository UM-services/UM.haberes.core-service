package um.haberes.core.exception.view;

public class TotalItemNotFoundException extends RuntimeException {
    public TotalItemNotFoundException(Integer anho, Integer mes, Integer codigoId) {

        super("Cannot found TotalItem " + anho + "/" + mes + "/" + codigoId);
    }
}
