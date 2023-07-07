package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import um.haberes.rest.model.Auditable
import um.haberes.rest.model.Persona
import java.math.BigDecimal

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["legajoId", "anho", "mes", "codigoId"])])
data class Item(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var itemId: Long? = null,
    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var codigoId: Int? = null,
    var codigoNombre: String = "",
    var importe: BigDecimal = BigDecimal.ZERO,

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null,

    @OneToOne
    @JoinColumn(name = "codigoId", insertable = false, updatable = false)
    var codigo: Codigo? = null

) : Auditable() {
    fun legajoKey(): String {
        return this.legajoId.toString() + "." + this.anho + "." + this.mes + "." + this.codigoId
    }

}
