package um.haberes.core.hexagonal.liquidaciones.general.domain.model;

/** Motivo por el que un legajo quedó omitido o fallido en un proceso masivo. */
public enum MotivoResultadoGeneral {

    BLOQUEADA,
    SIN_CONTROL,
    PERSONA_INEXISTENTE,
    PERSONA_NO_LIQUIDABLE,
    SIN_LIQUIDACION_ANTERIOR,
    PERIODO_NO_LIQUIDABLE,
    SIN_LIQUIDACION,
    NO_LIQUIDABLE,
    ERROR
}
