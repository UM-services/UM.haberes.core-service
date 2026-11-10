package um.haberes.core.hexagonal.crossslice.liquidacion.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/** Vista de lectura de la liquidación de un legajo y período, para consumo de otros slices. */
public record LiquidacionView(Long liquidacionId, Integer anho, Integer mes, Byte bloqueado,
                              Integer estado, String liquida, BigDecimal totalRemunerativo,
                              BigDecimal totalNoRemunerativo, BigDecimal totalDeduccion,
                              BigDecimal totalNeto, OffsetDateTime fechaLiquidacion,
                              OffsetDateTime fechaAcreditacion, Integer dependenciaId, String salida) {
}
