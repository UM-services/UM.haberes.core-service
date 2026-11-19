package um.haberes.core.hexagonal.liquidaciones.individual.domain.model;

/** Resultado de una operación de liquidación individual, con mensaje listo para mostrar. */
public record ResultadoProcesoIndividual(EstadoProcesoIndividual resultado, String mensaje) {

    public static ResultadoProcesoIndividual de(EstadoProcesoIndividual resultado, String mensaje) {
        return new ResultadoProcesoIndividual(resultado, mensaje);
    }

    public boolean exitoso() {
        return switch (resultado) {
            case LIQUIDADO, ELIMINADA, GENERADOS, GUARDADA -> true;
            default -> false;
        };
    }
}
