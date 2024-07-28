package um.haberes.core.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table
data class CargoTipo(

    @Id
    var cargoTipoId: Int? = null,

    var aCargo: Byte = 0,
    var nombre: String = "",
    var precedencia: Int = 0

) : Auditable()
