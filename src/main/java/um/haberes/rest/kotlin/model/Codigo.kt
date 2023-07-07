package um.haberes.rest.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import um.haberes.rest.model.Auditable

@Entity
@Table
data class Codigo(

    @Id
    var codigoId: Int? = null,
    var nombre: String = "",
    var docente: Byte = 0,
    var noDocente: Byte = 0,
    var transferible: Byte = 0,
    var incluidoEtec: Byte = 0

) : Auditable()
