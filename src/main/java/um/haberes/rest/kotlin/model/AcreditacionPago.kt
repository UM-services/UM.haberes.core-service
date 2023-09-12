package um.haberes.rest.kotlin.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import um.haberes.rest.model.Auditable
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["anho", "mes", "fechaPago"])])
data class AcreditacionPago(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var acreditacionPagoId: Long? = null,

    var anho: Int = 0,
    var mes: Int = 0,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaPago: OffsetDateTime? = null,
    var totalSantander: BigDecimal = BigDecimal.ZERO,
    var totalOtrosBancos: BigDecimal = BigDecimal.ZERO,
    var comprobanteIdPago: Int? = null,
    var puntoVentaPago: Int? = null,
    var numeroComprobantePago: Long? = null

) : Auditable()
