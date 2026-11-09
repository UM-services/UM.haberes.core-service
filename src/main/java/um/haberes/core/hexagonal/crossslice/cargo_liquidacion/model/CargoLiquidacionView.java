package um.haberes.core.hexagonal.crossslice.cargo_liquidacion.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/** Vista de lectura de un cargo liquidado, para consumo de otros slices. */
public record CargoLiquidacionView(Long cargoLiquidacionId, Integer dependenciaId,
                                   String dependenciaAcronimo, OffsetDateTime fechaDesde,
                                   OffsetDateTime fechaHasta, Integer categoriaId,
                                   String categoriaNombre, BigDecimal categoriaBasico,
                                   BigDecimal estadoDocente, BigDecimal horasJornada, int jornada,
                                   int presentismo, String situacion, boolean docente) {
}
