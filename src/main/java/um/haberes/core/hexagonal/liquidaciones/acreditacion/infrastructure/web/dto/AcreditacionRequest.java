package um.haberes.core.hexagonal.liquidaciones.acreditacion.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
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
public class AcreditacionRequest {

    @NotNull(message = "El año es obligatorio")
    private Integer anho;

    @NotNull(message = "El mes es obligatorio")
    private Integer mes;

    private Byte acreditado;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime limiteNovedades;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaContable;

    private Integer ordenContable;

    private BigDecimal sueldosOriginal;

    private BigDecimal sueldosAjustados;

    private BigDecimal contribucionesPatronales;
}
