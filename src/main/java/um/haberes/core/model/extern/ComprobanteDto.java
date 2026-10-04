package um.haberes.core.model.extern;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ComprobanteDto {

    private Integer comprobanteId = null;

    private String descripcion = "";

    private Integer tipoTransaccionId = null;

    private Byte ordenPago = 0;

    private Byte aplicaPendiente = 0;

    private Byte cuentaCorriente = 0;

    private Byte debita = 0;

    private long diasVigencia = 0;

    private Byte facturacionElectronica = 0;

    private Integer comprobanteAfipId = null;

    private Integer puntoVenta = null;

    private String letraComprobante = null;
}
