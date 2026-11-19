package um.haberes.core.hexagonal.liquidaciones.general.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/** Resultado persistido de un legajo dentro de un proceso masivo. */
public record ResultadoLegajoGeneral(Long detalleId, Long legajoId, String apellido, String nombre,
                                     EstadoLegajoGeneral resultado, MotivoResultadoGeneral motivo,
                                     String detalle, BigDecimal totalNeto, OffsetDateTime registradoEn) {
}
