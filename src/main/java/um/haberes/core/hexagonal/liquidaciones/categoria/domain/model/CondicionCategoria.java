package um.haberes.core.hexagonal.liquidaciones.categoria.domain.model;

/**
 * Condiciones del catálogo que se pueden filtrar en el buscador de categorías.
 * Se combinan entre sí con OR (una categoría se muestra si cumple al menos una).
 */
public enum CondicionCategoria {

    DOCENTE,

    NO_DOCENTE,

    LIQUIDA_POR_HORA,

    /** Cargo que se liquida por puntos (puntos &gt; 0). */
    ETEC
}
