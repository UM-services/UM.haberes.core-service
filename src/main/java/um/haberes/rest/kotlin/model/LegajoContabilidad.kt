package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import um.haberes.rest.model.Auditable
import java.math.BigDecimal

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["legajoId", "anho", "mes"])])
data class LegajoContabilidad(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var legajoContabilidadId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var diferencia: Byte = 0,
    var remunerativo: BigDecimal = BigDecimal.ZERO,
    var noRemunerativo: BigDecimal = BigDecimal.ZERO

) : Auditable()
