package um.haberes.core.hexagonal.etec.control_etec.application.exception;

/** El período ya tiene control ETEC y la copia no debe sobrescribirlo. */
public class ControlEtecYaExisteException extends ControlEtecException {

    public ControlEtecYaExisteException(Integer anho, Integer mes) {
        super("El período " + anho + "/" + mes + " ya tiene control ETEC");
    }
}
