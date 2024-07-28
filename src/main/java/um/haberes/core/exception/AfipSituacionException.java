package um.haberes.core.exception;

public class AfipSituacionException extends RuntimeException {
    public AfipSituacionException(Integer afipSituacionId) {
        super("Cannot find AfipSituacionId=" + afipSituacionId);
    }

}
