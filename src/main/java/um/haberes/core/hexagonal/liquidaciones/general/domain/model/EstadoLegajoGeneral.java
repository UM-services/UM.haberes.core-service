package um.haberes.core.hexagonal.liquidaciones.general.domain.model;

/** Resultado de un legajo dentro de un proceso masivo. */
public enum EstadoLegajoGeneral {

    /** El legajo quedó liquidado en el período. */
    LIQUIDADO,
    /** Se eliminó la liquidación del legajo en el período. */
    ELIMINADO,
    /** Se generaron los cargos del legajo en el período. */
    GENERADO,
    /** El legajo no se procesó por una regla de negocio. */
    OMITIDO,
    /** La operación falló para el legajo. */
    FALLIDO
}
