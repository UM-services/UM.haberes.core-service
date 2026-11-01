package um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.application.exception;

public class LegajoCategoriaImputacionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LegajoCategoriaImputacionException() {
        super("No se pudo encontrar el LegajoCategoriaImputacion");
    }

    public LegajoCategoriaImputacionException(Long legajoCategoriaImputacionId) {
        super("No se pudo encontrar el LegajoCategoriaImputacion con id: " + legajoCategoriaImputacionId);
    }
}
