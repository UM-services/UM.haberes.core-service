package um.haberes.core.kotlin.model.extern

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.OffsetDateTime

data class CuentaMovimientoDto(

    var cuentaMovimientoId: Long? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaContable: OffsetDateTime? = null,

    var ordenContable: Int = 0,
    var item: Int = 0,
    var numeroCuenta: BigDecimal? = null,
    var debita: Byte = 0,
    var comprobanteId: Int? = null,
    var concepto: String = "",
    var importe: BigDecimal = BigDecimal.ZERO,
    var proveedorId: Int? = null,
    var numeroAnulado: Int = 0,
    var version: Int = 0,
    var proveedorMovimientoId: Long? = null,
    var proveedorMovimientoIdOrdenPago: Long? = null,
    var apertura: Byte = 0,
    var trackId: Long? = null,
    var cuentaDto: CuentaDto? = null

)
