package um.haberes.rest.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import um.haberes.rest.model.Auditable

@Entity
@Table
data class ModoLiquidacion(

    @Id
    var modoLiquidacionId: Int? = null,
    var descripcion: String = ""

) : Auditable()
