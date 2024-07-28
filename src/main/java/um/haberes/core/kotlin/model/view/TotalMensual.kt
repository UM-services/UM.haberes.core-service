package um.haberes.core.kotlin.model.view

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Immutable
import java.math.BigDecimal

@Entity
@Table(name = "vw_total_mensual")
@Immutable
data class TotalMensual(

    @Id
    var uniqueId: String? = null,

    var anho: Int? = null,
    var mes: Int? = null,
    var codigoId: Int? = null,
    var total: BigDecimal = BigDecimal.ZERO

)
