package um.haberes.rest.kotlin.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["anho", "mes"])])
data class Acreditacion(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var acreditacionId: Long? = null,

    var anho: Int = 0,
    var mes: Int = 0,
    var acreditado: Byte = 0,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var limiteNovedades: OffsetDateTime? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaContable: OffsetDateTime? = null,

    var ordenContable: Int? = null,
    var sueldosOriginal: BigDecimal = BigDecimal.ZERO,
    var sueldosAjustados: BigDecimal = BigDecimal.ZERO,
    var contribucionesPatronales: BigDecimal = BigDecimal.ZERO

) : Auditable()
