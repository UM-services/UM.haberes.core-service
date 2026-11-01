package um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.application.exception;

public class LegajoContabilidadException extends RuntimeException {

    private static final long serialVersionUID = -1936202151163845889L;

    public LegajoContabilidadException() {
        super("No se pudo encontrar el LegajoContabilidad");
    }

    public LegajoContabilidadException(Long legajoContabilidadId) {
        super("No se pudo encontrar el LegajoContabilidad con id: " + legajoContabilidadId);
    }

    public LegajoContabilidadException(Long legajoId, Integer anho, Integer mes) {
        super("No se pudo encontrar el LegajoContabilidad " + legajoId + "/" + anho + "/" + mes);
    }
}
