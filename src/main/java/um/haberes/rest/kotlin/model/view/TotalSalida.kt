package um.haberes.rest.kotlin.model.view

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Immutable
import java.math.BigDecimal

@Entity
@Table(name = "vw_total_salida")
@Immutable
data class TotalSalida(

    @Id
    var uniqueId: String? = null,

    var anho: Int? = null,
    var mes: Int? = null,
    var totalRemunerativo: BigDecimal = BigDecimal.ZERO,
    var totalNoRemunerativo: BigDecimal = BigDecimal.ZERO,
    var totalDeduccion: BigDecimal = BigDecimal.ZERO,
    var totalNeto: BigDecimal = BigDecimal.ZERO

)
