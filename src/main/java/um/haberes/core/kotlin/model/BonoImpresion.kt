package um.haberes.core.kotlin.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table
data class BonoImpresion(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var bonoImpresionId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var legajoIdSolicitud: Long? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fecha: OffsetDateTime? = null,

    var ipAddress: String = ""

) : Auditable()
