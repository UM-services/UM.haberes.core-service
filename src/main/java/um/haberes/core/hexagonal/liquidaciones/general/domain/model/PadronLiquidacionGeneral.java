package um.haberes.core.hexagonal.liquidaciones.general.domain.model;

import java.util.List;

/** Padrón agregado de liquidación general de un período. */
public record PadronLiquidacionGeneral(Integer anho, Integer mes, AccionesLiquidacionGeneral acciones,
                                       ResumenPadron resumen, List<FilaLiquidacionGeneral> filas) {
}
