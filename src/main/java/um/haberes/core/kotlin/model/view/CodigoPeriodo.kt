package um.haberes.core.kotlin.model.view

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.Immutable

@Entity
@Table(name = "vw_codigo_periodo",
    uniqueConstraints = [UniqueConstraint(columnNames = ["anho", "mes", "codigoId"])])
@Immutable
data class CodigoPeriodo(

    @Id
    var codigoId: Int? = null,

    var anho: Int? = null,
    var mes: Int? = null

)
