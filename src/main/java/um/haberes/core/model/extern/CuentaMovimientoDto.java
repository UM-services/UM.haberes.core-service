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
public class CuentaMovimientoDto {

    private Long cuentaMovimientoId = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaContable = null;

    private int ordenContable = 0;

    private int item = 0;

    private BigDecimal numeroCuenta = null;

    private Byte debita = 0;

    private Integer comprobanteId = null;

    private String concepto = "";

    private BigDecimal importe = BigDecimal.ZERO;

    private Integer proveedorId = null;

    private int numeroAnulado = 0;

    private int version = 0;

    private Long proveedorMovimientoId = null;

    private Long proveedorMovimientoIdOrdenPago = null;

    private Byte apertura = 0;

    private Long trackId = null;

    private CuentaDto cuentaDto = null;
}
