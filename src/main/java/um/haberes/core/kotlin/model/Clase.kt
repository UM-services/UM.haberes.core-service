package um.haberes.core.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table
data class Clase(

    @Id
    var claseId: Int? = null,

    var nombre: String = "",
    var valorHora: BigDecimal = BigDecimal.ZERO

) : Auditable()
