package um.haberes.core.exception.view;

public class TotalNovedadException extends RuntimeException {
    public TotalNovedadException(Integer anho, Integer mes, Integer codigoId) {

        super("Cannot found TotalNovedad " + anho + "/" + mes + "/" + codigoId);
    }
}
