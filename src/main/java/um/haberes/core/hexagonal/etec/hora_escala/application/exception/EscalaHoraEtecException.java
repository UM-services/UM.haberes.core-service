package um.haberes.core.hexagonal.etec.hora_escala.application.exception;

/** Error de negocio de la escala de horas ETEC (responde 400). */
public class EscalaHoraEtecException extends RuntimeException {

    public EscalaHoraEtecException(String message) {
        super(message);
    }
}
