package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model;

import java.math.BigDecimal;

/**
 * Datos del período que condicionan la generación de cargos: el modo de liquidación (con o sin
 * fusión) y los parámetros ETEC por puntos del período ({@code control_etec}).
 */
public record ControlGeneracionCargos(int modoLiquidacionId, BigDecimal valorPuntoEtec,
                                      BigDecimal montoFijoCargo) {
}
