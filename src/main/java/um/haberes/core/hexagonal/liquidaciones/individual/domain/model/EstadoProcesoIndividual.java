package um.haberes.core.hexagonal.liquidaciones.individual.domain.model;

/** Resultado posible de una operación de liquidación individual. */
public enum EstadoProcesoIndividual {

    /** La liquidación se calculó y persistió. */
    LIQUIDADO,
    /** La liquidación del legajo/período se eliminó. */
    ELIMINADA,
    /** Se regeneraron los cargos docentes y no docentes. */
    GENERADOS,
    /** La novedad se guardó (alta o actualización). */
    GUARDADA,

    /** La liquidación del período está bloqueada. */
    BLOQUEADA,
    /** No hay registro de control para el período. */
    SIN_CONTROL,
    /** La persona no está en estado liquidable y la liquidación anterior sí lo estaba. */
    SIN_LIQUIDACION_ANTERIOR,
    /** La persona no está en estado liquidable. */
    PERSONA_NO_LIQUIDABLE,
    /** No hay cargos activos, cargos con clase ni novedades especiales. */
    PERIODO_NO_LIQUIDABLE,
    /** El motor no dejó liquidación pese a pasar los pre-chequeos. */
    NO_LIQUIDABLE,
    /** El motor no eliminó la liquidación pese a pasar los pre-chequeos. */
    NO_ELIMINADA,
    /** No hay liquidación del legajo/período. */
    SIN_LIQUIDACION,
    /** La novedad indicada no pertenece al legajo/período. */
    SIN_NOVEDAD,
    /** Faltan datos obligatorios en el pedido. */
    DATOS_INVALIDOS,
    /** El legajo no existe. */
    PERSONA_INEXISTENTE
}
