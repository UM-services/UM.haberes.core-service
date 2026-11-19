package um.haberes.core.hexagonal.liquidaciones.general.domain.model;

/** Operación masiva que ejecuta un proceso de liquidación general. */
public enum TipoProcesoGeneral {

    /** Genera los cargos del período para cada legajo (omite los bloqueados). */
    CARGOS,
    /** Liquida el período para cada legajo. */
    LIQUIDAR,
    /** Elimina la liquidación del período de cada legajo. */
    ELIMINAR
}
