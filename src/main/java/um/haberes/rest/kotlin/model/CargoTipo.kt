package um.haberes.rest.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import um.haberes.rest.model.Auditable

@Entity
@Table
data class CargoTipo(

    @Id
    var cargoTipoId: Int? = null,

    var aCargo: Byte = 0,
    var nombre: String = "",
    var precedencia: Int = 0

) : Auditable()
