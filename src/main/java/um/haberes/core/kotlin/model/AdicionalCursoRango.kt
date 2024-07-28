package um.haberes.core.kotlin.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
data class AdicionalCursoRango(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var adicionalCursoRangoId: Long? = null,

    var horasDesde: Int = 0,
    var horasHasta: Int = 0,
    var porcentaje: BigDecimal = BigDecimal.ZERO,
    var adicionalCursoTablaId: Long? = null

) : Auditable()
