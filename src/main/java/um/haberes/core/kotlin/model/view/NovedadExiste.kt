package um.haberes.core.kotlin.model.view

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Immutable

@Entity
@Table(name = "vw_novedad_existe")
@Immutable
data class NovedadExiste(

    @Id
    var uniqueId: String? = null,

    var legajoId: Long? = null,
    var anho: Int? = null,
    var mes: Int? = null

)
