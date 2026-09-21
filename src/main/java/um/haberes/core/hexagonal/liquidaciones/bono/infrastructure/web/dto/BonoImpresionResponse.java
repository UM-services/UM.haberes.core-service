package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BonoImpresionResponse {

    private Long bonoImpresionId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private Long legajoIdSolicitud;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fecha;

    private String ipAddress;
}
