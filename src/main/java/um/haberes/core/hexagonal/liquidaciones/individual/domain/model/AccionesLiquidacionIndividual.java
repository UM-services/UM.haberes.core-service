package um.haberes.core.hexagonal.liquidaciones.individual.domain.model;

/**
 * Acciones estructuralmente posibles sobre el legajo/período.
 *
 * <p>El bloqueo no se resuelve acá: se informa con {@code bloqueado}/{@code motivoBloqueo} y el
 * backend vuelve a validarlo en cada escritura. La UI sólo deshabilita lo imposible.
 */
public record AccionesLiquidacionIndividual(boolean puedeGenerarCargos, boolean puedeLiquidar,
                                            boolean puedeEliminarLiquidacion, boolean puedeEditarNovedades,
                                            boolean bloqueado, String motivoBloqueo) {
}
