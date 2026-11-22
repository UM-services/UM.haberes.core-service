package um.haberes.core.hexagonal.etec.presentismo.application.exception;

public class EtecPresentismoException extends RuntimeException {

    private static final long serialVersionUID = 3287142096513984321L;

    public EtecPresentismoException(String message) {
        super(message);
    }

    public EtecPresentismoException(Long presentismoId) {
        super("No existe el registro de presentismo ETEC " + presentismoId);
    }
}
