package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import um.haberes.rest.model.Auditable
import java.math.BigDecimal

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["dependenciaId", "facultadId", "geograficaId", "cargoClaseid"])])
data class CargoClaseImputacion(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cargoClaseImputacionId: Long? = null,

    var dependenciaId: Int? = null,
    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    var cargoClaseId: Long? = null,
    var cuentaSueldos: BigDecimal? = null,
    var cuentaAportes: BigDecimal? = null,

    ) : Auditable() {
    fun key(): String {
        return dependenciaId.toString() + "." + facultadId + "." + geograficaId + "." + cargoClaseId
    }

}
