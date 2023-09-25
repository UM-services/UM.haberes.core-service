package um.haberes.rest.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import um.haberes.rest.model.Auditable
import java.math.BigDecimal

@Entity
@Table
data class Clase(

    @Id
    var claseId: Int? = null,

    var nombre: String = "",
    var valorHora: BigDecimal = BigDecimal.ZERO

) : Auditable()
