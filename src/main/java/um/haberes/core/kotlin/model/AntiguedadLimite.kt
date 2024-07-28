package um.haberes.core.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table
data class AntiguedadLimite(

    @Id
    var desde: Int = 0,

    var hasta: Int = 0,
    var porcentaje: Int = 0

) : Auditable()
