package um.haberes.core.hexagonal.liquidaciones.bono.application.exception;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.FaltanteBono;

public class BonoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final transient List<FaltanteBono> faltantes;

    public BonoException(String message) {
        super(message);
        this.faltantes = null;
    }

    public BonoException(String message, List<FaltanteBono> faltantes) {
        super(message);
        this.faltantes = faltantes;
    }

    public List<FaltanteBono> getFaltantes() {
        return faltantes;
    }
}
