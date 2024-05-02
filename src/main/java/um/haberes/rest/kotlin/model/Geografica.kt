package um.haberes.rest.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal

@Entity
data class Geografica(

    @Id
    var geograficaId: Int? = null,

    var nombre: String = "",
    var reducido: String = "",
    var desarraigo: BigDecimal = BigDecimal.ZERO,
    var geograficaIdReemplazo: Int? = null

) : Auditable()
