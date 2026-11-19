package um.haberes.core.hexagonal.liquidaciones.general.domain.model;

/**
 * Resultado de intentar iniciar un proceso masivo.
 *
 * <p>Si {@code proceso} no es nulo, el proceso quedó encolado. Si es nulo, {@code motivo} explica
 * por qué no se inició (por ejemplo {@code SIN_CONTROL} o {@code SIN_LIQUIDABLES}).</p>
 */
public record InicioProcesoGeneral(ProcesoGeneral proceso, String motivo, String mensaje) {

    public static InicioProcesoGeneral iniciado(ProcesoGeneral proceso) {
        return new InicioProcesoGeneral(proceso, null, null);
    }

    public static InicioProcesoGeneral noIniciado(String motivo, String mensaje) {
        return new InicioProcesoGeneral(null, motivo, mensaje);
    }

    public boolean iniciadoProceso() {
        return proceso != null;
    }
}
