package um.haberes.rest.kotlin.view

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Immutable
import um.haberes.rest.model.Auditable
import java.math.BigDecimal

@Entity
@Table(name = "vw_categoria_search")
@Immutable
data class CategoriaSearch(

    @Id
    var categoriaId: Int? = null,
    var nombre: String? = null,
    var basico: BigDecimal? = null,
    var search: String? = null

) : Auditable()
