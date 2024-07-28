package um.haberes.core.kotlin.model.extern

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.OffsetDateTime

data class EjercicioDto(

    var ejercicioId: Int? = null,
    var nombre: String? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaInicio: OffsetDateTime? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaFinal: OffsetDateTime? = null,
    var bloqueado: Byte = 0,
    var ordenContableResultado: Int? = null,
    var ordenContableBienesUso: Int? = null

)
