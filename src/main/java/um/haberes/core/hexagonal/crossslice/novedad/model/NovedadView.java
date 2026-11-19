package um.haberes.core.hexagonal.crossslice.novedad.model;

import java.math.BigDecimal;

/** Vista de lectura de una novedad, para consumo de otros slices. */
public record NovedadView(Long novedadId, Integer codigoId, String codigoNombre, BigDecimal importe,
                          String value, String observaciones, Integer dependenciaId) {
}
