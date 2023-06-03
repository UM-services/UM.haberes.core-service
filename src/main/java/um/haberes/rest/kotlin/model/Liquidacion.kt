package um.haberes.rest.kotlin.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import um.haberes.rest.model.Auditable
import um.haberes.rest.model.Persona
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["legajoId", "anho", "mes"])])
data class Liquidacion(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var liquidacionId: Long? = null,
    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,

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
    var liquida: String = "",

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null,

    @OneToOne
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    var dependencia: Dependencia? = null

) : Auditable() {

    fun key(): String {
        return this.legajoId.toString() + "." + this.anho + "." + this.mes
    }

}
