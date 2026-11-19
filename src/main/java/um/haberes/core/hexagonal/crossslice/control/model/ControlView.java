package um.haberes.core.hexagonal.crossslice.control.model;

/** Vista de lectura del control de un período, para consumo de otros slices. */
public record ControlView(Long controlId, Integer anho, Integer mes, Integer modoLiquidacionId) {
}
