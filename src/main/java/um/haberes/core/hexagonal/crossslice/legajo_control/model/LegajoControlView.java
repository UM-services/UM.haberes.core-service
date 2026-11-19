package um.haberes.core.hexagonal.crossslice.legajo_control.model;

/** Vista de lectura del control de un legajo y período, para consumo de otros slices. */
public record LegajoControlView(Long legajoControlId, Long legajoId, Integer anho, Integer mes,
                                Byte liquidado, Byte fusionado, Byte bonoEnviado) {
}
