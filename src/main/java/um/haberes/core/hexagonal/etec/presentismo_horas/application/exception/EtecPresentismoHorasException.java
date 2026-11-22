package um.haberes.core.hexagonal.etec.presentismo_horas.application.exception;

public class EtecPresentismoHorasException extends RuntimeException {

    private static final long serialVersionUID = -7338164210597314265L;

    public EtecPresentismoHorasException(String message) {
        super(message);
    }

    public EtecPresentismoHorasException(Long presentismoHorasId) {
        super("No existe el registro de presentismo ETEC por horas " + presentismoHorasId);
    }
}
