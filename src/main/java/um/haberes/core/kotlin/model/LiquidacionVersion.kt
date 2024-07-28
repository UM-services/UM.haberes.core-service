package um.haberes.core.kotlin.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["legajoId", "anho", "mes", "version"])])
data class LiquidacionVersion(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var liquidacionVersionId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var version: Int = 0,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaLiquidacion: OffsetDateTime? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaAcreditacion: OffsetDateTime? = null,

    var dependenciaId: Int? = null,
    var salida: String? = null,
    var totalRemunerativo: BigDecimal = BigDecimal.ZERO,
    var totalNoRemunerativo: BigDecimal = BigDecimal.ZERO,
    var totalDeduccion: BigDecimal = BigDecimal.ZERO,
    var totalNeto: BigDecimal = BigDecimal.ZERO,
    var bloqueado: Byte = 0,
    var estado: Int = 0,
    var liquida: String = ""

) : Auditable()
