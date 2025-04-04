package um.haberes.core.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table
data class Situacion(

    @Id
    var situacionId: Int? = null,

    var nombre: String = "",
    var interino: Byte = 0,
    var ordinario: Byte = 0,
    var planta: Byte = 0,
    var contratado: Byte = 0,
    var secundario: Byte = 0

) : Auditable()
