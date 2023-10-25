package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import um.haberes.rest.model.Auditable
import java.math.BigDecimal

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["anho", "mes"])])
data class SeguridadSocial(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var seguridadSocialId: Long? = null,

    var anho: Int = 0,
    var mes: Int = 0,
    var cc351: BigDecimal = BigDecimal.ZERO,
    var cc301: BigDecimal = BigDecimal.ZERO,
    var cc352: BigDecimal = BigDecimal.ZERO,
    var cc302: BigDecimal = BigDecimal.ZERO,
    var cc312: BigDecimal = BigDecimal.ZERO,
    var cc028: BigDecimal = BigDecimal.ZERO

) : Auditable()
