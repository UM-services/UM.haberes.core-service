package um.haberes.core.kotlin.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table
data class CargoLiquidacionVersion(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cargoLiquidacionVersionId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int? = null,
    var mes: Int? = null,
    var version: Int? = 0,
    var dependenciaId: Int? = null,
    var categoriaId: Int? = null,
    var basico: BigDecimal = BigDecimal.ZERO,
    var estadoDocente: BigDecimal = BigDecimal.ZERO,
    var horasJornada: BigDecimal = BigDecimal.ZERO,
    var jornada: Int = 0,
    var presentismo: Int = 0,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaDesde: OffsetDateTime? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaHasta: OffsetDateTime? = null,

    var situacion: String? = null

) : Auditable()
