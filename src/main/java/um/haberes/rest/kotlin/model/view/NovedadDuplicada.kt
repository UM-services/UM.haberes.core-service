package um.haberes.rest.kotlin.model.view

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Immutable

@Entity
@Table(name = "vw_novedad_duplicada")
@Immutable
data class NovedadDuplicada(

    @Id
    var unified: String? = null,

    var legajoId: Long? = null,
    var anho: Int? = null,
    var mes: Int? = null,
    var codigoId: Int? = null,
    var cantidad: Int? = null

)
