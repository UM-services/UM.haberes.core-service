package um.haberes.core.kotlin.model.view

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.Immutable
import java.math.BigDecimal

@Entity
@Table(
    name = "vw_asignado_clase",
    uniqueConstraints = [UniqueConstraint(columnNames = ["legajoId", "dependenciaId", "cargoClaseId"])]
)
@Immutable
data class AsignadoClase(

    @Id
    var uniqueId: String? = null,
    var legajoId: Long? = null,
    var dependenciaId: Int? = null,
    var cargoClaseId: Long? = null,
    var periodoDesde: Long? = null,
    var periodoHasta: Long? = null,
    var basicoDesde: BigDecimal = BigDecimal.ZERO,
    var basicoHasta: BigDecimal = BigDecimal.ZERO

)
