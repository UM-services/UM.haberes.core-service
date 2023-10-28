package um.haberes.rest.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import um.haberes.rest.model.Auditable

@Entity
@Table
data class Designacion(

    @Id
    var categoriaId: Int? = null,

    val designacionTipoId: Int? = null,
    val cargoTipoId: Int? = null,
    val anual: Byte = 0,
    val semestral: Byte = 0,
    val aCargo: Byte = 0

) : Auditable()
