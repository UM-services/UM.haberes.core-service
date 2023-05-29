package um.haberes.rest.kotlin.model

import um.haberes.rest.model.Auditable
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table
data class Contacto(

    @Id
    var legajoId: Long? = null,
    var fijo: String = "",
    var movil: String = "",
    var mailPersonal: String = "",
    var mailInstitucional: String = ""

) : Auditable()
