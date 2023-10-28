package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import um.haberes.rest.model.Auditable
import java.math.BigDecimal

@Entity
@Table
data class ItemVersion(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var itemVersionId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var codigoId: Int? = null,
    var codigoNombre: String = "",
    var importe: BigDecimal = BigDecimal.ZERO

) : Auditable()
