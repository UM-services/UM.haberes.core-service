package um.haberes.rest.kotlin.model.view

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.Immutable
import java.math.BigDecimal

@Entity
@Table(
    name = "vw_novedad_acumulado",
    uniqueConstraints = [UniqueConstraint(columnNames = ["legajoId", "anho", "mes", "codigoId"])]
)
@Immutable
data class NovedadAcumulado(

    @Id
    var unified: String? = null,

    var legajoId: Long? = null,
    var anho: Int? = null,
    var mes: Int? = null,
    var codigoId: Int? = null,
    var importe: BigDecimal? = null,
    var cantidad: Int? = null

)
