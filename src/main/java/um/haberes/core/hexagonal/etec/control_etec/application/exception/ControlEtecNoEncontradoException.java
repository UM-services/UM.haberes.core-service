package um.haberes.core.hexagonal.etec.control_etec.application.exception;

/** No existe el control ETEC del período solicitado. */
public class ControlEtecNoEncontradoException extends ControlEtecException {

    public ControlEtecNoEncontradoException(Integer anho, Integer mes) {
        super("No existe el control ETEC del período " + anho + "/" + mes);
    }
}
