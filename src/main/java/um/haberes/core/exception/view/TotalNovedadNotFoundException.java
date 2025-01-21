package um.haberes.core.exception.view;

public class TotalNovedadNotFoundException extends RuntimeException {
    public TotalNovedadNotFoundException(Integer anho, Integer mes, Integer codigoId) {

        super("Cannot found TotalNovedad " + anho + "/" + mes + "/" + codigoId);
    }
}
