package um.haberes.rest.kotlin.model.view

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.Immutable

@Entity
@Table(name = "vw_categoria_by_periodo",
    uniqueConstraints = [UniqueConstraint(columnNames = ["anho", "mes", "categoriaId"])])
@Immutable
data class CategoriaByPeriodo(

    @Id
    var uniqueId: String? = null,

    var anho: Int? = null,
    var mes: Int? = null,
    var categoriaId: Int? = null

)
