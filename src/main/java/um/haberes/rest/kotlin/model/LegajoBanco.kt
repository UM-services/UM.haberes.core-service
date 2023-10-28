package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import um.haberes.rest.model.Auditable
import java.math.BigDecimal

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["legajoId", "anho", "mes", "cbu"])])
data class LegajoBanco(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var legajoBancoId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var cbu: String = "",
    var fijo: BigDecimal = BigDecimal.ZERO,
    var porcentaje: BigDecimal = BigDecimal.ZERO,
    var resto: Byte = 0,
    var acreditado: BigDecimal = BigDecimal.ZERO,

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null,

    @OneToOne
    @JoinColumns(
        JoinColumn(name = "legajoId", referencedColumnName = "legajoId", insertable = false, updatable = false),
        JoinColumn(name = "anho", referencedColumnName = "anho", insertable = false, updatable = false),
        JoinColumn(name = "mes", referencedColumnName = "mes", insertable = false, updatable = false)
    )
    var liquidacion: Liquidacion? = null

) : Auditable()
