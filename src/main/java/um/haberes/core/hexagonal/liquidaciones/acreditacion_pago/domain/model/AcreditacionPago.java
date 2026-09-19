package um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

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
public class AcreditacionPago {

    private Long acreditacionPagoId;

    private Integer anho;

    private Integer mes;

    private OffsetDateTime fechaPago;

    private BigDecimal totalSantander;

    private BigDecimal totalOtrosBancos;

    private Integer comprobanteIdPago;

    private Integer puntoVentaPago;

    private Long numeroComprobantePago;
}
