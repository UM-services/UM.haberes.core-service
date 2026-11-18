package um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/** Estado de un proceso masivo de liquidación general. */
public record ProcesoGeneralResponse(String procesoId, String tipo, Integer anho, Integer mes,
                                     String estado, int total, int procesados, int progreso, int exitosos,
                                     int omitidos, int fallidos, boolean forzar, Long iniciadoPor,
                                     @JsonFormat(shape = JsonFormat.Shape.STRING,
                                             pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
                                     OffsetDateTime iniciadoEn,
                                     @JsonFormat(shape = JsonFormat.Shape.STRING,
                                             pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
                                     OffsetDateTime finalizadoEn,
                                     String mensaje) {
}
