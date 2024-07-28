package um.haberes.core.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table
data class CodigoGrupo(

    @Id
    var codigoId: Int? = null,
    var remunerativo: Byte = 0,
    var noRemunerativo: Byte = 0,
    var deduccion: Byte = 0,
    var total: Byte = 0,

    @OneToOne
    @JoinColumn(name = "codigoId", insertable = false, updatable = false)
    var codigo: Codigo? = null

) : Auditable()
