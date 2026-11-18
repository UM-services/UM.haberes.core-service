package um.haberes.core.hexagonal.etec.control_etec.application.exception;

/** No existe el control ETEC del período anterior para poder copiarlo. */
public class ControlEtecOrigenException extends ControlEtecException {

    public ControlEtecOrigenException(Integer anho, Integer mes) {
        super("No existe el control ETEC del período anterior a " + anho + "/" + mes);
    }
}
