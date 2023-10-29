package um.haberes.rest.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table
data class DesignacionTipo(

    @Id
    var designacionTipoId: Int = 0,

    var nombre: String = "",
    var horasSemanales: BigDecimal = BigDecimal.ZERO,
    var horasTotales: BigDecimal = BigDecimal.ZERO,
    var simples: Int = 0

) : Auditable()
