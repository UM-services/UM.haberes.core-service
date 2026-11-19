package um.haberes.core.hexagonal.liquidaciones.general.domain.model;

/** Acciones posibles sobre el período completo, según el control y el estado del padrón. */
public record AccionesLiquidacionGeneral(boolean hayControl, boolean puedeGenerarCargos,
                                         boolean puedeLiquidar, boolean puedeEliminar) {
}
