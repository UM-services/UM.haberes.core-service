package um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.web.dto;

import java.math.BigDecimal;
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
public class LiquidacionPeriodoResponse {

    private Long liquidacionId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaLiquidacion;

    private Integer dependenciaId;

    private String salida;

    private BigDecimal totalRemunerativo;

    private BigDecimal totalNoRemunerativo;

    private BigDecimal totalDeduccion;

    private BigDecimal totalNeto;

    private Byte bloqueado;

    private Long periodo;
}
