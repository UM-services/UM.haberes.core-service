package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model;

/**
 * Universo de cargos que se genera en {@code cargo_liquidacion}.
 *
 * <p>Cada universo tiene un alcance de borrado propio y disjunto de los demás, derivado de los
 * datos de {@code categoria}: las categorías docentes comunes, las no docentes comunes y las
 * categorías ETEC (con {@code puntos > 0}).</p>
 */
public enum UniversoCargos {

    DOCENTE,

    NO_DOCENTE,

    ETEC
}
