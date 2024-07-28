package um.haberes.core.kotlin.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["legajoId", "anho", "mes", "codigoId"])])
data class Item(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var itemId: Long? = null,
    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var codigoId: Int? = null,
    var codigoNombre: String = "",
    var importe: BigDecimal = BigDecimal.ZERO,

    @OneToOne(optional = true)
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null,

    @OneToOne(optional = true)
    @JoinColumn(name = "codigoId", insertable = false, updatable = false)
    var codigo: Codigo? = null,

) : Auditable() {
    fun legajoKey(): String {
        return this.legajoId.toString() + "." + this.anho + "." + this.mes + "." + this.codigoId
    }

}
