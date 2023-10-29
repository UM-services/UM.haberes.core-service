package um.haberes.rest.kotlin.model.internal

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.OffsetDateTime

data class OrdenPagoRequest(

    var anho: Int? = null,
    var mes: Int? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaPago: OffsetDateTime? = null,
    var totalSantander: BigDecimal? = null,
    var totalOtrosBancos: BigDecimal? = null,
    var individual: Boolean = false

)
