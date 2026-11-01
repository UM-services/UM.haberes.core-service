package um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.application.exception;

public class LegajoCargoClaseImputacionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LegajoCargoClaseImputacionException() {
        super("No se pudo encontrar el LegajoCargoClaseImputacion");
    }

    public LegajoCargoClaseImputacionException(Long legajoCargoClaseImputacionId) {
        super("No se pudo encontrar el LegajoCargoClaseImputacion con id: " + legajoCargoClaseImputacionId);
    }
}
