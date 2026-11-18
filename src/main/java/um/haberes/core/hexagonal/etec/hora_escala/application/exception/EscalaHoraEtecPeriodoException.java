package um.haberes.core.hexagonal.etec.hora_escala.application.exception;

/** El período no tiene control ETEC, así que la escala de horas no puede existir (responde 404). */
public class EscalaHoraEtecPeriodoException extends RuntimeException {

    public EscalaHoraEtecPeriodoException(Integer anho, Integer mes) {
        super("El período " + anho + "/" + mes + " no tiene control ETEC");
    }
}
