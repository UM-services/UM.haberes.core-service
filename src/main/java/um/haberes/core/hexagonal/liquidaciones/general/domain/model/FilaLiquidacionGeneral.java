package um.haberes.core.hexagonal.liquidaciones.general.domain.model;

import java.math.BigDecimal;

/** Fila del padrón de liquidación general: una persona liquidable del período. */
public record FilaLiquidacionGeneral(int orden, Long legajoId, String apellido, String nombre,
                                     Integer estado, String liquida, Integer dependenciaId, String salida,
                                     boolean esDocente, boolean esNoDocente, boolean tieneNovedades,
                                     boolean liquidado, boolean bloqueado, BigDecimal totalRemunerativo,
                                     BigDecimal totalNoRemunerativo, BigDecimal totalDeduccion,
                                     BigDecimal totalNeto) {
}
