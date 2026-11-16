package um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/** Resultado de un legajo dentro de un proceso masivo. */
public record ResultadoLegajoGeneralResponse(Long detalleId, Long legajoId, String apellido, String nombre,
                                             String resultado, String motivo, String detalle,
                                             BigDecimal totalNeto,
                                             @JsonFormat(shape = JsonFormat.Shape.STRING,
                                                     pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
                                             OffsetDateTime registradoEn) {
}
