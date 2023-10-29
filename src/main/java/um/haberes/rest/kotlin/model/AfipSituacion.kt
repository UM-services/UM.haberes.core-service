package um.haberes.rest.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table
data class AfipSituacion(

    @Id
    var afipSituacionId: Int = 0,

    var descripcion: String = ""

) : Auditable()
