package um.haberes.core.kotlin.model.view

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Immutable
import um.haberes.core.kotlin.model.Auditable

@Entity
@Table(name = "vw_codigo_search")
@Immutable
data class CodigoSearch(

    @Id
    var codigoId: Int? = null,
    var nombre: String? = null,
    var docente: Byte? = null,
    var noDocente: Byte? = null,
    var search: String? = null

) : Auditable()
