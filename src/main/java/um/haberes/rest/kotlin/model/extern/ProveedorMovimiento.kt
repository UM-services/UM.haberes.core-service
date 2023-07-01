package um.haberes.rest.kotlin.model.extern

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.OffsetDateTime

data class ProveedorMovimiento(

    var proveedorMovimientoId: Long? = null,
    var proveedorId: Int? = null,
    var nombreBeneficiario: String = "",
    var comprobanteId: Int? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaComprobante: OffsetDateTime? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaVencimiento: OffsetDateTime? = null,
    var prefijo: Int = 0,
    var numeroComprobante: Long = 0L,
    var netoSinDescuento: BigDecimal = BigDecimal.ZERO,
    var descuento: BigDecimal = BigDecimal.ZERO,
    var neto: BigDecimal = BigDecimal.ZERO,
    var importe: BigDecimal = BigDecimal.ZERO,
    var cancelado: BigDecimal = BigDecimal.ZERO,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaContable: OffsetDateTime? = null,
    var ordenContable: Int? = null,
    var concepto: String? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaAnulacion: OffsetDateTime? = null,
    var conCargo: Byte = 0,
    var solicitaFactura: Byte = 0,
    var geograficaId: Int? = null,
    var comprobante: Comprobante? = null

)
