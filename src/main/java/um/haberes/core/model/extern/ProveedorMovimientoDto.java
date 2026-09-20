package um.haberes.core.model.extern;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorMovimientoDto {

    private Long proveedorMovimientoId = null;

    private Integer proveedorId = null;

    private String nombreBeneficiario = "";

    private Integer comprobanteId = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaComprobante = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaVencimiento = null;

    private int prefijo = 0;

    private long numeroComprobante = 0L;

    private BigDecimal netoSinDescuento = BigDecimal.ZERO;

    private BigDecimal descuento = BigDecimal.ZERO;

    private BigDecimal neto = BigDecimal.ZERO;

    private BigDecimal importe = BigDecimal.ZERO;

    private BigDecimal cancelado = BigDecimal.ZERO;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaContable = null;

    private Integer ordenContable = null;

    private String concepto = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaAnulacion = null;

    private Byte conCargo = 0;

    private Byte solicitaFactura = 0;

    private Integer geograficaId = null;

    private ComprobanteDto comprobanteDto = null;
}
