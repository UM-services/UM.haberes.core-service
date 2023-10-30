package um.haberes.rest.kotlin.model.view

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.Immutable
import java.math.BigDecimal

@Entity
@Table(
    name = "vw_imputado_aporte",
    uniqueConstraints = [UniqueConstraint(columnNames = ["anho", "mes", "cuentaAportes"])]
)
@Immutable
data class ImputadoAporte(

    @Id
    var uniqueId: String? = null,

    var anho: Int? = null,
    var mes: Int? = null,
    var cuentaAportes: BigDecimal? = null,
    var totalImputado: BigDecimal? = null

)
