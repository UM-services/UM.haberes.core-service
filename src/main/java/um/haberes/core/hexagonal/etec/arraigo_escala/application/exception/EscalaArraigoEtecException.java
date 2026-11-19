package um.haberes.core.hexagonal.etec.arraigo_escala.application.exception;

/** Error de negocio de la escala de arraigo ETEC (responde 400). */
public class EscalaArraigoEtecException extends RuntimeException {

    public EscalaArraigoEtecException(String message) {
        super(message);
    }
}
