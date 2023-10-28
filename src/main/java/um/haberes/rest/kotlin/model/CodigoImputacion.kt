package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import um.haberes.rest.model.Auditable
import java.math.BigDecimal

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["dependenciaId", "facultadId", "geograficaId", "codigoId"])])
data class CodigoImputacion(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var codigoImputacionId: Long? = null,

    var dependenciaId: Int? = null,
    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    var codigoId: Int? = null,
    var cuentaSueldosDocente: BigDecimal? = null,
    var cuentaAportesDocente: BigDecimal? = null,
    var cuentaSueldosNoDocente: BigDecimal? = null,
    var cuentaAportesNoDocente: BigDecimal? = null


) : Auditable() {
    fun key(): String {
        return dependenciaId.toString() + "." + facultadId + "." + geograficaId + "." + codigoId
    }

}
