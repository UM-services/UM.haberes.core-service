package um.haberes.rest.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table
data class AfipConceptoSueldo(

    @Id
    var afipConceptoSueldoId: Long? = null,
    var descripcion: String = "",
    var asignado: Byte = 0

) : Auditable()
