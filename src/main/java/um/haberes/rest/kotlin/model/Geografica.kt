package um.haberes.rest.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import um.haberes.rest.model.Auditable
import java.math.BigDecimal

@Entity
@Table
data class Geografica(

    @Id
    var geograficaId: Int? = null,

    var nombre: String = "",
    var reducido: String = "",
    var desarraigo: BigDecimal = BigDecimal.ZERO,
    var geograficaIdReemplazo: Int? = null

) : Auditable()
