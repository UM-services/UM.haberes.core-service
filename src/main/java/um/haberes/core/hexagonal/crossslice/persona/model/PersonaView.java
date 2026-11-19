package um.haberes.core.hexagonal.crossslice.persona.model;

import java.math.BigDecimal;

/**
 * Vista de lectura de una persona para consumo de otros slices.
 *
 * <p>Es un contrato de cross-slice, no el dominio de {@code personas/persona}: sólo expone los
 * campos que los consumidores necesitan y aísla los cambios internos de ese slice.
 */
public record PersonaView(Long legajoId, BigDecimal documento, String apellido, String nombre,
                          Integer estado, String liquida, Integer dependenciaId, String salida) {
}
