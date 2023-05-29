package um.haberes.rest.kotlin.model.extern

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.OffsetDateTime

data class Cuenta(

    var numeroCuenta: BigDecimal? = null,
    var nombre: String = "",
    var integradora: Byte = 0,
    var grado: Int = 0,
    var grado1: BigDecimal? = null,
    var grado2: BigDecimal? = null,
    var grado3: BigDecimal? = null,
    var grado4: BigDecimal? = null,
    var geograficaId: Int? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaBloqueo: OffsetDateTime? = null,
    var visible: Byte = 0,
    var cuentaContableId: Long? = null

)
