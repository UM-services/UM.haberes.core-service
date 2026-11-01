package um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.infrastructure.web.dto;

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
public class AcreditacionPagoResponse {

    private Long acreditacionPagoId;

    private Integer anho;

    private Integer mes;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaPago;

    private BigDecimal totalSantander;

    private BigDecimal totalOtrosBancos;

    private Integer comprobanteIdPago;

    private Integer puntoVentaPago;

    private Long numeroComprobantePago;
}
