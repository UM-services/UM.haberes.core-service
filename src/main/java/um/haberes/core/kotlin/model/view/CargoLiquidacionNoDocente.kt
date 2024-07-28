package um.haberes.core.kotlin.model.view

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Immutable

@Entity
@Table(name = "vw_cargo_liquidacion_no_docente")
@Immutable
data class CargoLiquidacionNoDocente(

    @Id
    var uniqueId: String? = null,

    var legajoId: Long? = null,
    var anho: Int? = null,
    var mes: Int? = null

)
