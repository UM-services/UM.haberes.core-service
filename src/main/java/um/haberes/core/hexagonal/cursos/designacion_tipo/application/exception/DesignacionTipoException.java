package um.haberes.core.hexagonal.cursos.designacion_tipo.application.exception;

import java.math.BigDecimal;

public class DesignacionTipoException extends RuntimeException {

    private static final long serialVersionUID = -8759168362280238248L;

    public DesignacionTipoException() {
        super("Cannot find DesignacionTipo");
    }

    public DesignacionTipoException(BigDecimal horassemanales) {
        super("Cannot find DesignacionTipo " + horassemanales);
    }

    public DesignacionTipoException(Integer designaciontipoId) {
        super("Cannot find DesignacionTipo " + designaciontipoId);
    }
}
