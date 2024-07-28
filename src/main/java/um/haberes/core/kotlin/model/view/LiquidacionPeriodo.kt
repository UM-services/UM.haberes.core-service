package um.haberes.core.kotlin.model.view

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.hibernate.annotations.Immutable
import um.haberes.core.kotlin.model.Auditable
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(name = "vw_liquidacion_periodo")
@Immutable
data class LiquidacionPeriodo(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var liquidacionId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaLiquidacion: OffsetDateTime? = null,

    var dependenciaId: Int? = null,
    var salida: String? = null,
    var totalRemunerativo: BigDecimal = BigDecimal.ZERO,
    var totalNoRemunerativo: BigDecimal = BigDecimal.ZERO,
    var totalDeduccion: BigDecimal = BigDecimal.ZERO,
    var totalNeto: BigDecimal = BigDecimal.ZERO,
    var bloqueado: Byte = 0,
    var periodo: Long? = null

) : Auditable()
