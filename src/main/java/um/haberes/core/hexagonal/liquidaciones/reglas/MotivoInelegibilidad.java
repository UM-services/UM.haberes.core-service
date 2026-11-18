package um.haberes.core.hexagonal.liquidaciones.reglas;

/** Motivos por los que un legajo no es elegible para una operación de liquidación. */
public enum MotivoInelegibilidad {

    /** La liquidación del período está bloqueada y no se pidió forzar. */
    BLOQUEADA,
    /** El legajo no existe. */
    PERSONA_INEXISTENTE,
    /** No hay una liquidación anterior válida que respalde la baja. */
    SIN_LIQUIDACION_ANTERIOR,
    /** La persona no está en estado liquidable. */
    PERSONA_NO_LIQUIDABLE
}
